package com.anat.userlistapp.fake

import com.anat.userlistapp.service.UserResponseList
import com.anat.userlistapp.model.*
import com.anat.userlistapp.service.RemoteService


class FakeRemoteService : RemoteService {

    private val fakeUsers = (1..15).map {
        UserResponse(
            gender = if (it % 2 == 0) "male" else "female",
            name = Name(
                title = if (it % 2 == 0) "Mr" else "Ms",
                first = "FirstName$it",
                last = "LastName$it"
            ),
            location = Location(
                street = Street(number = "$it", name = "StreetName$it"),
                city = "City$it",
                state = "State$it",
                country = "Country$it",
                postcode = "$it$it$it$it",
                coordinates = Coordinates(latitude = "48.${it}66", longitude = "2.${it}22"),
                timezone = Timezone(offset = "+0$it:00", description = "Timezone$it")
            ),
            email = "user$it@gmail.com",
            login = Login(uuid = "uuid-$it", username = "user$it"),
            dob = Dob(date = "1990-01-${it.toString().padStart(2, '0')}", age = (30 + it).toString()),
            registered = Registered(date = "2020-01-${it.toString().padStart(2, '0')}", age = "4"),
            phone = "+3312345678$it",
            cell = "+3371234567$it",
            id = Id(name = "ID", value = "ID-$it"),
            picture = Picture(
                large = "https://example.com/large$it.jpg",
                medium = "https://example.com/medium$it.jpg",
                thumbnail = "https://example.com/thumbnail$it.jpg"
            ),
            nat = "FR"
        )
    }

    override suspend fun getUsers(results: Int, page: Int, seed: String): UserResponseList {
        return UserResponseList(results = fakeUsers.take(results))
    }
}
