package com.example.userside

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.userside.databinding.FragmentFavoruiteBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoruiteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoruiteFragment : Fragment(), ClickInterface {
    // TODO: Rename and change types of parameters
    lateinit var binding : FragmentFavoruiteBinding
    lateinit var mainActivity: MainActivity
    private var param1: String? = null
    private var param2: String? = null
    var db = Firebase.firestore
    var exerciseModel = ExerciseModel()
    var exerciseList = ArrayList<ExerciseModel>()
    lateinit var exerciseAdapter : ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db.collection("exercise")
            .whereEqualTo("likeId",mainActivity.auth.currentUser?.uid.toString())
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
                            exerciseList.add(exerciseModel)
                            println("LikeExercise: $exerciseList")
                            exerciseAdapter.notifyDataSetChanged()

                        }
                        DocumentChange.Type.MODIFIED->{
                            exerciseModel = snapshot.document.toObject(ExerciseModel::class.java)
                            exerciseModel.id = snapshot.document.id
                            var index =  exerciseList.indexOfFirst { element-> element.exerciseName == exerciseModel.exerciseName }
                            exerciseList.clear()
                            exerciseList.set(index,exerciseModel)
                            exerciseAdapter.notifyDataSetChanged()
                        }
                        DocumentChange.Type.REMOVED->{
                            exerciseModel = snapshot.document.toObject(ExerciseModel::class.java)
                            exerciseList.remove(exerciseModel)
                            exerciseAdapter.notifyDataSetChanged()
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
        binding = FragmentFavoruiteBinding.inflate(layoutInflater)
        exerciseAdapter = ExerciseAdapter(mainActivity,exerciseList,this)
        binding.rvList.layoutManager = LinearLayoutManager(mainActivity)
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
         * @return A new instance of fragment FavoruiteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoruiteFragment().apply {
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
        println("click")
    }
}