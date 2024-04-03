package com.example.userside

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.userside.databinding.FragmentExerciseDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseDetailFragment : Fragment() {
    lateinit var binding : FragmentExerciseDetailBinding
    lateinit var mainActivity: MainActivity
    var db = Firebase.firestore
    // TODO: Rename and change types of parameters
    var exerciseModel = ExerciseModel()
    lateinit var imageAdapter : ImageAdapter
    var imageList = ArrayList<ExerciseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        binding = FragmentExerciseDetailBinding.inflate(layoutInflater)
        arguments?.let {
            exerciseModel = it.getSerializable("exerciseModel") as ExerciseModel
            println("Exercise id: $exerciseModel")
        }
            binding.tvExerciseName.setText(exerciseModel.exerciseName)
            binding.tvExerciseDescription.setText(exerciseModel.exerciseDescription)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        imageAdapter = exerciseModel.imageList?.let { ImageAdapter(mainActivity, it) }!!
        binding.rvList.layoutManager = LinearLayoutManager(mainActivity,
            LinearLayoutManager.HORIZONTAL,false)
        binding.rvList.adapter = imageAdapter

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExerciseDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExerciseDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}