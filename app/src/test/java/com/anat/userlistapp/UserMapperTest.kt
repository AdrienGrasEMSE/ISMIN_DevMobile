package com.anat.userlistapp

import org.junit.Test


import com.anat.userlistapp.model.*
import com.anat.userlistapp.model.mapper.UserMapper
import org.junit.Assert.assertEquals

class UserMapperTest {

    private val userMapper = UserMapper()

    @Test
    fun `transformResponseToUser should map UserResponse to User correctly`() {
        val userResponse = UserResponse(
            gender = "male",
            name = Name(title = "Mr", first = "John", last = "Doe"),
            location = Location(
                street = Street(number = "123", name = "Main St"),
                city = "Springfield",
                state = "IL",
                country = "USA",
                postcode = "62704",
                coordinates = Coordinates(latitude = "39.7817", longitude = "-89.6501"),
                timezone = Timezone(offset = "-06:00", description = "Central Time")
            ),
            email = "john.doe@example.com",
            login = Login(uuid = "1234", username = "johndoe"),
            dob = Dob(date = "1990-01-01", age = "33"),
            registered = Registered(date = "2015-01-01", age = "8"),
            phone = "555-1234",
            cell = "555-5678",
            id = Id(name = "SSN", value = "123-45-6789"),
            picture = Picture(
                large = "https://example.com/images/large.jpg",
                medium = "https://example.com/images/medium.jpg",
                thumbnail = "https://example.com/images/thumbnail.jpg"
            ),
            nat = "US"
        )

        val user = userMapper.transformResponseToUser(userResponse)

        assertEquals("John Doe", user.fullName)
        assertEquals("john.doe@example.com", user.email)
        assertEquals("https://example.com/images/large.jpg", user.avatarUrl)
    }
}


