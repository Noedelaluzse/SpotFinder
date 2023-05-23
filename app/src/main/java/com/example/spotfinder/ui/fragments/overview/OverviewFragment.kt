package com.example.spotfinder.ui.fragments.overview

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.spotfinder.R
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.spotfinder.databinding.FragmentOverviewBinding
import com.example.spotfinder.models.Place
import com.example.spotfinder.models.comments.NewComment
import com.example.spotfinder.util.Constants.Companion.BUNDLE_LKEY
import com.example.spotfinder.viewmodels.CommentViewModel
import com.example.spotfinder.viewmodels.MainViewModel


class OverviewFragment : Fragment() {
    private  val commentsViewModel: CommentViewModel by activityViewModels()
    private  val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!
    private var placeId: String = ""
    private var userId: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        mainViewModel.getUserInfomationForfragment()
        mainViewModel.temp.observe(viewLifecycleOwner) {
            if (it != "") {
                Log.d("idUser", it.toString())
                userId = it
                //mainViewModel.getUserInfo(it)
            }
        }

        initListenners()
        val args = arguments
        val myBundle: Place? = args?.getParcelable(BUNDLE_LKEY)

        // TODO settear la imagen de mi lugar
        //binding.mainImageView.load(myBundle.image)
        binding.tvTitle.text = myBundle?.name
        binding.tvComments.text = myBundle?.comments.toString()
        binding.tvSummary.text = myBundle?.description
        binding.mainImageView.load("https://img.freepik.com/vector-gratis/apoye-concepto-negocio-local_23-2148592675.jpg") {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        placeId = myBundle?.id.toString()

        if (myBundle?.sportBar == true) {
            binding.imvSportBar.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.restaurant == true) {
            binding.imvRestaurante.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.bar == true) {
            binding.imvBar.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.cafe == true) {
            binding.imvCafe.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.nightclub == true) {
            binding.imvNightClub.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.liveMusic == true) {
            binding.imvLiveMusic.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.park == true) {
            binding.imvPark.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }

       /*  TODO agragar la propiedad others a la clase de Places
        if (myBundle?.others == true) {
            binding.imvOthers.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green)
        } */

        return binding.root
    }

    private fun initListenners() {
        binding.commentFab.setOnClickListener {

            onCreateDialog()
        }
    }

     fun onCreateDialog() {
         val builder: AlertDialog.Builder? = activity?.let {
             val builder = AlertDialog.Builder(it)
             val inflater = requireActivity().layoutInflater
             val view = inflater.inflate(R.layout.comment_layout, null)
             builder.setView(view)
             builder.apply {
                 val text = view.findViewById<EditText>(R.id.et_comment_user)

                 setPositiveButton(R.string.ok,
                     DialogInterface.OnClickListener { dialog, id ->
                         val comment = text.text.toString()
                         if (comment.isNotEmpty()) {
                             val newComment = NewComment(placeId, comment, userId )
                             Log.d("userCommet", newComment.toString())
                             commentsViewModel.createComment(newComment)
                             commentsViewModel.getComments(placeId)
                             Toast.makeText(context, "Comentario publicado", Toast.LENGTH_SHORT).show()
                         } else {
                             Toast.makeText(context, "Comentario no publicado", Toast.LENGTH_SHORT).show()
                         }
                     }
                 )
                 setNegativeButton("Cancelar",
                     DialogInterface.OnClickListener { dialog, id ->
                         dialog.dismiss()
                     }
                     )
             }
         }
         val dialog: AlertDialog? = builder?.create()
         dialog?.show()
    }

}