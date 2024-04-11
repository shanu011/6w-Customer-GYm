package com.example.userside

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.userside.databinding.FragmentLevel1Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [level1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class level1Fragment : Fragment() {
    lateinit var binding : FragmentLevel1Binding
    lateinit var mainActivity: MainActivity
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var level : Int =0
    var weightGainORLoss : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           level = it.getInt("level")
            weightGainORLoss= it.getInt("weightLossOrGain")
            println("Level: $level, weightGainORLoss: $weightGainORLoss")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentLevel1Binding.inflate(layoutInflater)
        binding.btnfull.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("level",level)
            bundle.putInt("weightGainORLoss",weightGainORLoss)
            bundle.putInt("exerciseType",1)
            findNavController().navigate(R.id.allExerciseFragment,bundle)
        }
        binding.btnarm.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("level",level)
            bundle.putInt("weightGainORLoss",weightGainORLoss)
            bundle.putInt("exerciseType",2)
            findNavController().navigate(R.id.allExerciseFragment,bundle)
        }
        binding.btnleg.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("level",level)
            bundle.putInt("weightGainORLoss",weightGainORLoss)
            bundle.putInt("exerciseType",3)
            findNavController().navigate(R.id.allExerciseFragment,bundle)
        }
        binding.btnchest.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("level",level)
            bundle.putInt("weightGainORLoss",weightGainORLoss)
            bundle.putInt("exerciseType",4)
            findNavController().navigate(R.id.allExerciseFragment,bundle)
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment level1ragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            level1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}