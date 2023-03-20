package me.vitornascimento.trendingrepositories.data.mapper

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import me.vitornascimento.trendingrepositories.data.local.entity.TrendingRepositoryEntity
import me.vitornascimento.trendingrepositories.data.remote.dto.RepositoryOwnerInfoDTO
import me.vitornascimento.trendingrepositories.data.remote.dto.TrendingRepositoriesResponse
import me.vitornascimento.trendingrepositories.data.remote.dto.TrendingRepositoryDTO
import me.vitornascimento.trendingrepositories.domain.model.TimeProvider
import me.vitornascimento.trendingrepositories.domain.model.TrendingRepository
import org.junit.After
import org.junit.Test

class TrendingRepositoriesMapperTest {

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `given TrendingRepositoryEntity when toDomainModel called verify its return`() {
        val id = 1
        val name = "Foo"
        val starsCount = 1
        val forksCount = 1
        val ownerUsername = "Foo"
        val ownerAvatarUrl = "Foo"
        val page = 1
        val createdAt = 1L

        val trendingRepositoryEntity = TrendingRepositoryEntity(
            id = id,
            name = name,
            starsCount = starsCount,
            forksCount = forksCount,
            ownerUsername = ownerUsername,
            ownerAvatarUrl = ownerAvatarUrl,
            page = page,
            createdAt = createdAt,
        )

        val expectedResult = TrendingRepository(
            name = name,
            starsCount = starsCount,
            forksCount = forksCount,
            ownerUsername = ownerUsername,
            ownerAvatarUrl = ownerAvatarUrl
        )
        val result = trendingRepositoryEntity.toDomainModel()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given TrendingRepositoriesResponse when toEntityModelList called verify its return`() {
        val id = 1
        val name = "Foo"
        val starsCount = 1
        val forksCount = 1
        val ownerUsername = "Foo"
        val ownerAvatarUrl = "Foo"
        val page = 1
        val createdAt = 1L

        val response = TrendingRepositoriesResponse(
            listOf(
                TrendingRepositoryDTO(
                    id = id,
                    name = name,
                    starsCount = starsCount,
                    forksCount = forksCount,
                    ownerInfo = RepositoryOwnerInfoDTO(
                        username = ownerUsername,
                        avatarUrl = ownerAvatarUrl
                    ),
                )
            )
        )

        val expectedResult = listOf(
            TrendingRepositoryEntity(
                id = id,
                name = name,
                starsCount = starsCount,
                forksCount = forksCount,
                ownerUsername = ownerUsername,
                ownerAvatarUrl = ownerAvatarUrl,
                page = page,
                createdAt = createdAt
            )
        )

        mockkObject(TimeProvider)
        every { TimeProvider.nowInMillis() } returns createdAt

        val result = response.toEntityModelList(page)

        assertEquals(expectedResult, result)
    }

}