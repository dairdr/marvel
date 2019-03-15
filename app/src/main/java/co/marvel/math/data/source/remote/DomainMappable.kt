package co.marvel.math.data.source.remote

interface DomainMappable<R> {
    fun asDomain(): R
}