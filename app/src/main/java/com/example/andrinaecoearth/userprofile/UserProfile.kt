package com.example.ecoearth

import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.annotation.RequiresApi
import com.example.andrinaecoearth.R
import com.example.andrinaecoearth.databinding.LayoutUserProfileBinding
import android.net.Uri
import android.provider.MediaStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*



class UserProfile : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: LayoutUserProfileBinding
    lateinit var sharedPreferences: SharedPreferences

    lateinit var profileBioText: EditText


        //for SharedPreference
        companion object {
            private const val PREF_USERNAME = "profile_username_input_text"
            private const val PREF_AGE = "profile_age_input_text"
            private const val PREF_GENDER = "gender"
            private const val PREF_BIRTHDAY = "birthday"
            private const val PREF_PROFILE_IMAGE_URI = "profileImageUri"

        }



    //for AdapterView.OnItemSelectedListener for genderSpinner
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val selectionTv = binding.selectedGenderTextView
        //select gender
        selectionTv.text = parent.getItemAtPosition(pos).toString()
    }
    override fun onNothingSelected(parent: AdapterView<*>) {
        //when nothing is selected
    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("UserProfilePrefs", MODE_PRIVATE)
        // Call the genderSpinner method to set up the spinner
        genderSpinner(binding.profileGenderSpinner)

        //set pre-saved bio text
        profileBioText = binding.profileBioText
        profileBioText.setText(getSavedBioText())

        //set pre-saved profile pic
        setProfilePic()
        val savedImageUri = sharedPreferences.getString("profileImageUri", null)
        if (!savedImageUri.isNullOrEmpty()) {
            binding.profileView.setImageURI(Uri.parse(savedImageUri))
        }


        binding.profileGenderSpinner.onItemSelectedListener = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setBirthdayDate()
        }

        binding.profileEditImageButton.setOnClickListener {
            // Function to check and request permission to access pictures/media
            fun checkPermission(permission: String, requestCode: Int) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(permission),
                        requestCode
                    )
                } else {
                    Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
                }
            }

        }


        //button to save user profile bio text description
        binding.profileBioSaveButton.setOnClickListener {
            saveBioText()
        }
        //load and set username
        binding.profileUsernameInputText.setText(getSavedUsername())

        //load and set age
        binding.profileAgeInputText.setText(getSavedAge())

        //load and set birthday
        binding.profileBirthdayInput.setText(getSavedBirthdayInput())

    }


    //get the real path of the image from the content URI
    private fun getImagePathFromUri(uri: Uri): String? {
        var imagePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val contentResolver: ContentResolver = applicationContext.contentResolver
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                imagePath = it.getString(columnIndex)
            }
        }
        return imagePath
    }


    //change user profile picture
    private fun setProfilePic() {
        Log.d("hello","hi")
        binding.profileEditImageButton.setOnClickListener {
            Log.d("the setonclicklistener","the setonclicklistener works")
            // Registers a photo picker activity launcher in single-select mode.
            val pickMedia =
                registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        // Load the selected image into profileView
                        binding.profileView.setImageURI(uri)

                        Log.d("shrek","it's setting the image uri")

                        // Get the real path of the image from the content URI
                        val imagePath = getImagePathFromUri(uri)


                        Log.d("save","it's saving")
                        //save the image URI to SharedPreferences
                        saveProfileImage(uri)
                    } else {
                        Log.d("PhotoPicker", "No media selected")
                    }
                }
            // Launch the photo picker and let the user choose only images
            pickMedia.launch("image/*")


        }
    }



    //save the image/profile pic
    private fun saveProfileImage(imageUri: Uri) {
        // Convert the Uri to a string
        val imagePath = imageUri.toString()

        // Save the image URI to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("profileImageUri", imagePath)
        editor.apply()

        // Show a Toast message indicating that the image has been saved
        Toast.makeText(this@UserProfile, "Profile image saved!", Toast.LENGTH_SHORT).show()
    }


    private fun getSavedUsername(): String {
        return sharedPreferences.getString(PREF_USERNAME, "") ?: ""
    }

    private fun getSavedAge(): String? {
        //default age value in case wrong value is entered
        val defaultValue = "0"
        val savedValue = sharedPreferences.getString(PREF_AGE, defaultValue)
        return savedValue

    }

    private fun getSavedBirthdayInput(): String {
        return sharedPreferences.getString(PREF_BIRTHDAY, "") ?: ""
    }



    private fun getSavedBioText(): String {
        return sharedPreferences.getString("bioText", "") ?: ""
    }

    //save user profile description
    private fun saveBioText() {
        val profileBioText = binding.profileBioText.text.toString()
        //also saves the username
        val profileUsername = binding.profileUsernameInputText.text.toString()
        //saves gender
        val profileGender = binding.selectedGenderTextView.text.toString()
        //saves age
        val profileAge = binding.profileAgeInputText.text.toString()
        //saves birthday
        val profileBirthday = binding.profileBirthdayInput.text.toString()


        // save the bio text to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("bioText", profileBioText) //saves profile description
        editor.putString(PREF_USERNAME, profileUsername) //saves username
        editor.putString(PREF_GENDER, profileGender) //saves gender
        editor.putString(PREF_AGE, profileAge) //saves age
        editor.putString(PREF_BIRTHDAY, profileBirthday) //saves birthday

        editor.apply()

        //show a Toast message saying Done
        Toast.makeText(this@UserProfile, "Saved successfully!", Toast.LENGTH_SHORT).show()
    }


    //@RequiresApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setBirthdayDate() {
        val setBirthday = binding.profileBirthdayInput

        //set OnClickListener on the Birthday EditText to show DatePickerDialog
        setBirthday.setOnClickListener {
            //get current date from Calendar instance
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            //create and show DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this@UserProfile,
                DatePickerDialog.OnDateSetListener { _, selectedDayOfMonth, selectedMonth, selectedYear ->
                    val date = LocalDate.of(selectedDayOfMonth, selectedMonth + 1, selectedYear)
                    val formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    setBirthday.setText(formattedDate)
                },
                dayOfMonth,
                month,
                year
            )
            datePickerDialog.show()

        }
    }


    //spinner for gender option
    private fun genderSpinner(profileGenderSpinner: Spinner) {
        //1. create object to hold spinner
        val spinnerGenderSelection = binding.profileGenderSpinner


        //create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
            this@UserProfile,
            R.array.profile_gender_selection,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            //specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //apply the adapter to the spinner
            profileGenderSpinner.adapter = adapter
        }

        // retrieve chosen gender from SharedPreferences
        val storedGender = sharedPreferences.getString(PREF_GENDER, "")
        if (storedGender != null) {
            //set the initial selection based on the stored gender
            val genderPosition = adapter.getPosition(storedGender)
            if (genderPosition != -1) {
                profileGenderSpinner.setSelection(genderPosition)
            }
        }

        //set listener for item selection
        profileGenderSpinner.onItemSelectedListener = this

    }
}