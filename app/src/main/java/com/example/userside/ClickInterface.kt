package com.example.userside


interface ClickInterface {
    fun onExerciseViewClick(exerciseModel : ExerciseModel)
    fun onLikeClick(exerciseModel: ExerciseModel)

}
interface DayClickInterface {
    fun onDayClick(dayModel : DayModel)

}