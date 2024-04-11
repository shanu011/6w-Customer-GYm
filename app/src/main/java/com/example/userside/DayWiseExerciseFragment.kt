package com.example.userside

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.userside.databinding.FragmentDayWiseExerciseBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DayWiseExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayWiseExerciseFragment : Fragment(), DayClickInterface {
    lateinit var binding : FragmentDayWiseExerciseBinding
    lateinit var mainActivity: MainActivity
    var db = Firebase.firestore
    lateinit var dayAdapter : DayAdapter
    var dayModel = DayModel()
    var dayList = ArrayList<DayModel>()
    private var difficultyLevel: Int = 1
    private var weightLossORGain: Int = 1
    var i = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            difficultyLevel = arguments?.getInt("difficulitylevel", 1) ?: 1
            weightLossORGain = it.getInt("weightLossOrGain",1)
            println("Check the Level: $difficultyLevel ")
            println("weightLossORGain $weightLossORGain ")
        }
       // dayList.clear()

        db.collection("day").whereEqualTo("difficultyLevel",difficultyLevel)
            .addSnapshotListener { value, error ->
                println("SnapshotListener")
                if(error!= null){
                    println("Error: ${error.message}")
                    return@addSnapshotListener
                }
                dayList.clear()
                for(snapshot in value!!.documentChanges){
                    when(snapshot.type){
                        DocumentChange.Type.ADDED->{
                            dayModel = snapshot.document.toObject(DayModel::class.java)
                            dayModel.id = snapshot.document.id
                            dayList.add(dayModel)
                            dayList.sortBy { it.day }
                            println("SortList: $dayList")
                            dayAdapter.notifyDataSetChanged()

                        }
                        DocumentChange.Type.MODIFIED->{
                            dayModel = snapshot.document.toObject(DayModel::class.java)
                            dayModel.id = snapshot.document.id
                            var index =  dayList.indexOfFirst { element-> element.id == snapshot.document.id }
                            dayList.set(index,dayModel)
                            dayList.sortBy { it.day }
                            dayAdapter.notifyDataSetChanged()
                        }
                        DocumentChange.Type.REMOVED->{
                            dayModel = snapshot.document.toObject(DayModel::class.java)
                            dayList.remove(dayModel)
                            dayList.sortBy { it.day }
                            dayAdapter.notifyDataSetChanged()
                        }
                    }
                }

            }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDayWiseExerciseBinding.inflate(layoutInflater)
        dayAdapter = DayAdapter(dayList,this)
        binding.rvList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvList.adapter = dayAdapter

        return  binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DayWiseExerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DayWiseExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDayClick(dayModel: DayModel) {
        var bundle = Bundle()
        bundle.putInt("level",difficultyLevel)
        bundle.putInt("position",weightLossORGain)
        bundle.putString("dayModel",dayModel.id)
        mainActivity.navController.navigate(R.id.exerciseFragment,bundle)
    }
}