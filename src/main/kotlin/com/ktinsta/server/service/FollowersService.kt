package com.ktinsta.server.service

import com.ktinsta.server.model.Followers
import com.ktinsta.server.repository.FollowersRepository
import org.springframework.stereotype.Service

@Service
class FollowersService(val userService: UserService, val repository: FollowersRepository) {
    fun subscribe(from: String, to: String){
        val current = userService.retrieveUserData(from)
        val next = userService.retrieveUserData(to)

        val follow = Followers(
            user = current!!,
            follower = next!!
        )

        //TODO(I think it is inefficient realization. Think about it.)
        if(!repository.findAll().map { Pair(it.user.id, it.follower.id) }.contains(Pair(current.id, next.id)))
            repository.save(follow)
    }

    fun unsubscribe(from: String, to: String){
        val current = userService.retrieveUserData(from)
        val next = userService.retrieveUserData(to)

        val follow = Followers(
            user = current!!,
            follower = next!!
        )

        //TODO(I think it is inefficient realization. Think about it.)
        follow.id = repository.findAll().filter { (it.user.id == current.id) && (it.follower.id == next.id) }.map { it.id }.first()

        repository.delete(follow)
    }
}