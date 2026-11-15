package com.example.voos.data


import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport WHERE name LIKE '%' || :query || '%' OR iata_code LIKE '%' || :query || '%' ORDER BY passengers DESC")
    fun findAirports(query: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code != :excludeCode ORDER BY name ASC")
    fun getAllAirportsExcept(excludeCode: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    fun getAirportByCode(iataCode: String): Flow<Airport?>

    @Query("SELECT * FROM airport ORDER BY passengers DESC")
    fun getAllAirports(): Flow<List<Airport>>
}