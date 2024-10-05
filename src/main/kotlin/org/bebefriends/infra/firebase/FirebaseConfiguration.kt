package org.bebefriends.infra.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FirebaseConfiguration{

    @Bean
    fun firebaseApp(): FirebaseApp {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build()
        return FirebaseApp.initializeApp(options)
    }
    @Bean
    fun firebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp())
    }

    @Bean
    fun firebaseMessaging():FirebaseMessaging {
        return FirebaseMessaging.getInstance(firebaseApp())
    }
}