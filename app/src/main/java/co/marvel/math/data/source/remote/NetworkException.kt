package co.marvel.math.data.source.remote

open class NetworkException(error: Throwable) : RuntimeException(error)

class NoNetworkException(error: Throwable) : NetworkException(error)

class ConnectionLostException(error: Throwable) : NetworkException(error)

class ServerUnreachableException(error: Throwable) : NetworkException(error)