package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.HistoryView

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.databinding.FragmentProfilepageBinding
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION




class ProfileFragment(): Fragment() {
    val GALLERY_REQUEST_CODE = 1
    val SHARED_PREF_PROFILE_PAGE = "profile-page"
    val SHARED_PREF_PROFILE_PIC = "profile-pic"
    private lateinit var binding: FragmentProfilepageBinding
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentProfilepageBinding.inflate(inflater, container, false)
        this.mView = binding.root
        binding.buttonChangePic.setOnClickListener {
            changeProfPic()
        }

        return mView
    }

    fun changeProfPic() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    var selectedImage = data?.getData()
                    binding.imageviewProfilePic.setImageURI(selectedImage)

                    val sharedPref = activity?.getSharedPreferences(SHARED_PREF_PROFILE_PAGE, Context.MODE_PRIVATE) ?: return
                    with(sharedPref.edit()) {
                        putString(SHARED_PREF_PROFILE_PIC, selectedImage.toString())
                        commit()
                    }
                }
                else -> {
                    return
                }
            }
        }
    }
}