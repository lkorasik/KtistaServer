import com.ktinsta.server.service.AppUserDetailsService
import com.ktinsta.server.storage.model.FullUser
import com.ktinsta.server.storage.repository.FullUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException


@ExtendWith(MockitoExtension::class)
class `App User Details Service` {
    @Mock
    private lateinit var fullUserRepository: FullUserRepository

    private lateinit var service: AppUserDetailsService

    private val username = "Einstein"
    private val password = "123456"

    @BeforeEach
    fun before() {
        service = AppUserDetailsService(fullUserRepository)
    }

    @Test
    fun `User exists`() {
        val fullUser = FullUser(username = username, password = password)
        Mockito.`when`(fullUserRepository.findByUsername(username)).thenReturn(fullUser)

        val details: UserDetails
        try {
            details = service.loadUserByUsername(username)
        } catch (e: UsernameNotFoundException) {
            fail("User must exists")
        }

        assertThat(details.username).isEqualTo(username)
        assertThat(details.password).isEqualTo(password)
    }

    @Test
    fun `User doesn't exists`() {
        Mockito.`when`(fullUserRepository.findByUsername(username)).thenReturn(null)

        try {
            service.loadUserByUsername(username)
            fail("User doesn't exists")
        } catch (e: UsernameNotFoundException){
            assert(true)
        }
    }
}