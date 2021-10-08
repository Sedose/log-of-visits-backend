package com.example.work.security

import com.example.work.exception.ErrorCode
import com.example.work.exception.GeneralException
import com.microsoft.graph.requests.extensions.GraphServiceClient
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class JwtTokenFilter(
    private val userDetailsService: UserDetailsService,
    private val authHeader: String,
) : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        request as HttpServletRequest
        val requestPath = request.requestURI.substring(request.contextPath.length)
        println("Securing path: $requestPath")
        resolveToken(request)?.let { token ->
            runCatching {
                getAuthentication(token).let {
                    SecurityContextHolder.getContext().authentication = it
                }
            }.onFailure {
                SecurityContextHolder.clearContext()
                sendError(response)
            }
        } ?: sendError(response)
        chain.doFilter(request, response)
    }

    private fun sendError(response: ServletResponse) {
        (response as HttpServletResponse).sendError(HttpStatus.FORBIDDEN.value())
        throw GeneralException(ErrorCode.ACCESS_TOKEN_INVALID)
    }

    private fun getAuthentication(accessToken: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(
            retrieveUserMail(accessToken)
        )
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(authHeader)
    }

    private fun retrieveUserMail(accessToken: String) =
        retrieveUser(accessToken)
            .mail

    private fun retrieveUser(accessToken: String) =
        GraphServiceClient.builder()
            .authenticationProvider(SimpleAuthProvider(accessToken))
            .buildClient()
            .me()
            .buildRequest()
            .select("mail")
            .get()
}
