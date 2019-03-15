package co.marvel.math.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.marvel.math.data.source.*
import co.marvel.math.view.home.MainViewModel
import retrofit2.Retrofit

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val retrofit: Retrofit
) : ViewModelProvider.NewInstanceFactory() {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
      with(modelClass) {
        when {
          isAssignableFrom(MainViewModel::class.java) -> {
              val charactersRepository = CharacterRepository.default(retrofit)
              val comicsRepository = ComicsRepository.default(retrofit)
              val creatorsRepository = CreatorsRepository.default(retrofit)
              val eventsRepository = EventsRepository.default(retrofit)
              val seriesRepository = SeriesRepository.default(retrofit)
              val storiesRepository = StoriesRepository.default(retrofit)
            MainViewModel(
                    charactersRepository,
                    comicsRepository,
                    creatorsRepository,
                    eventsRepository,
                    seriesRepository,
                    storiesRepository
            )
          }
          else -> {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
          }
        }
      } as T

  companion object {
    @Volatile
    private var instance: ViewModelFactory? = null

    @JvmStatic
    fun default(): ViewModelFactory =
        instance ?: synchronized(ViewModelFactory::class.java) {
          val retrofit = Injection.provideRetrofit()
          instance ?: ViewModelFactory(retrofit).also {
            instance = it
          }
        }
  }
}