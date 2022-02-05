package com.ktinsta.server.service

import com.ktinsta.server.storage.repository.FollowersRepository
import com.ktinsta.server.storage.model.Followers
import org.springframework.stereotype.Service

@Service
class FollowersService(val userService: UserService, val repository: FollowersRepository) {
    fun subscribe(from: String, to: String){
        val current = userService.retrieveFullUserData(from)
        val next = userService.retrieveFullUserData(to)

        val follow = Followers(
            fullUser = current!!,
            follower = next!!
        )

        //TODO(I think it is inefficient realization. Think about it.)
        if(!repository.findAll().map { Pair(it.fullUser.id, it.follower.id) }.contains(Pair(current.id, next.id)))
            repository.save(follow)
    }

    fun unsubscribe(from: String, to: String){
        val current = userService.retrieveFullUserData(from)
        val next = userService.retrieveFullUserData(to)

        val follow = Followers(
            fullUser = current!!,
            follower = next!!
        )

        //TODO(I think it is inefficient realization. Think about it.)
        follow.id = repository.findAll().filter { (it.fullUser.id == current.id) && (it.follower.id == next.id) }.map { it.id }.first()

        repository.delete(follow)
    }

    fun getAllFollowings(id: Long): List<Followers> {
        val user = userService.retrieveUserData(id)
        if(user != null)
            return repository.findByUser(user)
        else
            return emptyList()
    }
}