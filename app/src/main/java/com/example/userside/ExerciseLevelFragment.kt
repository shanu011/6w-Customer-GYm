package com.example.userside

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.userside.databinding.FragmentExerciseLevelBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseLevelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseLevelFragment : Fragment() {
    lateinit var binding : FragmentExerciseLevelBinding
    lateinit var mainActivity: MainActivity
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var position : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            position = it.getInt("position",1)
            println("Position: $position")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExerciseLevelBinding.inflate(layoutInflater)
        binding.btnBeginners.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt("level",1)
            bundle.putInt("weightLossOrGain",position)
            mainActivity.navController.navigate(R.id.allExerciseFragment,bundle)
        }
        binding.btnIntermidiate.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt("level",2)
            bundle.putInt("weightLossOrGain",position)
            mainActivity.navController.navigate(R.id.allExerciseFragment,bundle)
        }
        binding.btnAdvance.setOnClickListener {
            var bundle = Bundle()
            bundle.putInt("level",3)
            bundle.putInt("weightLossOrGain",position)
            mainActivity.navController.navigate(R.id.allExerciseFragment,bundle)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mainActivity.binding.bottomNav.visibility = View.VISIBLE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExerciseLevelFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExerciseLevelFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}