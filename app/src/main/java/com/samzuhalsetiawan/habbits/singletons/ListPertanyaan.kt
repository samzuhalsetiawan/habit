package com.samzuhalsetiawan.habbits.singletons

import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.models.OpsiJawaban
import com.samzuhalsetiawan.habbits.models.Pertanyaan

object ListPertanyaan {
    val listPertanyaan = listOf(
        Pertanyaan(
            question = "Berapa jam biasanya anda tidur sehari?",
            types = Habit.Types.HEALTH,
            answers = listOf(
                OpsiJawaban("Kurang dari 6 jam", 1),
                OpsiJawaban("Diantara 6 sampai 8 jam", 3),
                OpsiJawaban("Lebih dari 8 jam", 2),
            )
        ),
        Pertanyaan(
            question = "Apakah anda terlalu fokus masa lalu atau masa yang akan datang?",
            types = Habit.Types.SELF_DEVELOPMENT,
            answers = listOf(
                OpsiJawaban("Tidak, saya lebih focus pada saat ini", 1),
                OpsiJawaban("Ya, kadang-kadang", 2),
                OpsiJawaban("Ya, lebih sering", 3),
            )
        ),
        Pertanyaan(
            question = "Apakah menurut anda kehidupan sehari-hari anda sudah terorganisir?",
            types = Habit.Types.PRODUCTIVITY,
            answers = listOf(
                OpsiJawaban("Saya merasa terorganisir", 3),
                OpsiJawaban("Dalam tahap berusaha", 2),
                OpsiJawaban("Masih berantakan", 1),
            )
        ),
        Pertanyaan(
            question = "Bagaimana anda menggambarkan pola makan anda?",
            types = Habit.Types.HEALTH,
            answers = listOf(
                OpsiJawaban("kurang teratur", 1),
                OpsiJawaban("saya mencoba untuk meningkatkan", 2),
                OpsiJawaban("seimbang", 3),
            )
        ),
        Pertanyaan(
            question = "bagaimana fokus Anda pada pengembangan pribadi Anda?",
            types = Habit.Types.SELF_DEVELOPMENT,
            answers = listOf(
                OpsiJawaban("aku bahkan tidak tahu harus mulai dari mana", 1),
                OpsiJawaban("Aku mencoba untuk fokus, tapi aku selalu terganggu", 2),
                OpsiJawaban("saya menetapkan tujuan dan tetap terorganisir", 3),
            )
        )
    )
}