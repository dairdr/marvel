package co.marvel.math.view.list.adapter

interface ResultOptionListener {
  fun onClickCharacter(id: String)
  fun onClickComic(id: String)
  fun onClickCreator(id: String)
  fun onClickEvent(id: String)
  fun onClickSerie(id: String)
  fun onClickStory(id: String)
}