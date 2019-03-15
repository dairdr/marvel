package co.marvel.math.view.list.adapter

abstract class ResultOption(
        val type: String,
        val id: String,
        val name: String,
        val description: String?,
        val thumbnail: String?
) {
    class TYPE {
        companion object {
            const val CHARACTER = "character"
            const val COMIC = "comic"
            const val CREATOR = "creator"
            const val EVENT = "event"
            const val SERIE = "serie"
            const val STORY = "story"
        }
    }
}

data class CharacterOption(
        val identifier: String,
        val title: String,
        val detail: String,
        val avatar: String
) : ResultOption(TYPE.CHARACTER, identifier, title, detail, avatar)

data class ComicOption(
        val identifier: String,
        val title: String,
        val detail: String,
        val format: String,
        val avatar: String
) : ResultOption(TYPE.COMIC, identifier, title, detail, avatar)

data class CreatorOption(
        val identifier: String,
        val firstName: String,
        val middleName: String,
        val lastName: String,
        val fullName: String,
        val avatar: String
) : ResultOption(TYPE.CREATOR, identifier, fullName, null, avatar)

data class EventOption(
        val identifier: String,
        val title: String,
        val detail: String,
        val avatar: String,
        val start: String?,
        val end: String?
) : ResultOption(TYPE.EVENT, identifier, title, detail, avatar)

data class SerieOption(
        val identifier: String,
        val title: String,
        val detail: String,
        val avatar: String,
        val startYear: Int,
        val endYear: Int,
        val rating: String
) : ResultOption(TYPE.SERIE, identifier, title, detail, avatar)

data class StoryOption(
        val identifier: String,
        val title: String,
        val detail: String,
        val avatar: String?
) : ResultOption(TYPE.STORY, identifier, title, detail, avatar)