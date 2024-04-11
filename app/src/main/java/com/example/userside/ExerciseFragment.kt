package com.example.userside

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
import com.example.userside.databinding.FragmentExerciseBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment : Fragment(), ClickInterface {
    lateinit var binding : FragmentExerciseBinding
    var db = Firebase.firestore
    var dayId = ""
    var exerciseList = ArrayList<ExerciseModel>()
    var exerciseModel = ExerciseModel()
    lateinit var exerciseAdapter: ExerciseAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mainActivity: MainActivity
    var difficultyLevel  = 0
    var weightLossORGain : Int = 0
    var exerciseType : Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            difficultyLevel = it.getInt("level")
            weightLossORGain = it.getInt("weightGainORLoss")
            exerciseType = it.getInt("exerciseType")
            dayId = it.getString("dayModel").toString()
            println("difficultyLevel: $difficultyLevel,weightLossORGain $weightLossORGain, exerciseType: $exerciseType ")
            println("DayModelId: ${dayId}")
            println("weightLossORGain: ${weightLossORGain}")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExerciseBinding.inflate(layoutInflater)
        db.collection("exercise").whereEqualTo("dayId",dayId)
            .whereEqualTo("difficultLevel",difficultyLevel)
            .whereEqualTo("weigthGainOrLoss",weightLossORGain)
            .whereEqualTo("exerciseType",exerciseType)
            .addSnapshotListener { value, error ->
                if(error!= null){
                    println("Error: ${error.message}")
                    return@addSnapshotListener
                }

                for(snapshot in value!!.documentChanges){
                    print("SnapshotListener 1")
                    when(snapshot.type){
                        DocumentChange.Type.ADDED->{
                            exerciseModel = snapshot.document.toObject(ExerciseModel::class.java)
                            exerciseModel.id = snapshot.document.id
                            exerciseList.clear()
                            exerciseList.add(exerciseModel)
                            println("ExerciseList: $exerciseList")
                            exerciseAdapter.notifyDataSetChanged()

                        }
//                        DocumentChange.Type.MODIFIED->{
//                            exerciseModel = snapshot.document.toObject(ExerciseModel::class.java)
//                            exerciseModel.id = snapshot.document.id
//                            var index =  exerciseList.indexOfFirst { element-> element.exerciseName == exerciseModel.exerciseName }
//                            exerciseList.clear()
//                            exerciseList.set(index,exerciseModel)
//                            exerciseAdapter.notifyDataSetChanged()
//                        }
                        DocumentChange.Type.REMOVED->{
                            exerciseModel = snapshot.document.toObject(ExerciseModel::class.java)
                            exerciseList.remove(exerciseModel)
                            exerciseAdapter.notifyDataSetChanged()
                        }

                        else -> {}
                    }
                }
            }
        exerciseAdapter = ExerciseAdapter(requireContext(),exerciseList, this)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvList.layoutManager = linearLayoutManager
        binding.rvList.adapter = exerciseAdapter

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onExerciseViewClick(exerciseModel: ExerciseModel) {
        var bundle = Bundle()
        bundle.putSerializable("exerciseModel",exerciseModel)
        mainActivity.navController.navigate(R.id.exerciseDetailFragment, bundle)
    }

    override fun onLikeClick(exerciseModel: ExerciseModel) {
        println("AuthId: ${mainActivity.auth.currentUser?.uid}")
        if(exerciseModel.likeId?.contains(mainActivity.auth.currentUser?.uid ?:"",true)  == false){
            println("Inside the Function")
            exerciseModel.likeId = mainActivity.auth.currentUser?.uid.toString()
                db.collection("exercise").document(exerciseModel.id.toString()).update("likeId",exerciseModel.likeId).addOnCompleteListener {
                    Toast.makeText(mainActivity,"Liked",Toast.LENGTH_SHORT).show()
                }
        }else{
            exerciseModel.likeId = ""
            db.collection("exercise").document(exerciseModel.id.toString()).update("likeId",exerciseModel.likeId).addOnCompleteListener {
                Toast.makeText(mainActivity,"UnLike",Toast.LENGTH_SHORT).show()
            }

        }
    }
}