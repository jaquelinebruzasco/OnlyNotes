package com.jaquelinebruzasco.onlynotes.util

import java.text.Normalizer

fun String.normalizeString() = Normalizer.normalize(this, Normalizer.Form.NFD).replace(Regex("[^\\p{ASCII}]"), "").lowercase()