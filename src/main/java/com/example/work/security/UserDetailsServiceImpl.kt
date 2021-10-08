package com.example.work.security

import com.example.work.exception.ErrorCode
import com.example.work.exception.GeneralException
import com.example.work.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service("userDetailsServiceImpl")
open class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): SecurityUser {
        return SecurityUser.fromUser(
            userRepository.findByEmail(email)
                .orElseThrow { GeneralException(ErrorCode.DEFAULT) }
        )
    }
}
