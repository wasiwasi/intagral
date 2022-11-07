package com.ssafy.intagral.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import com.ssafy.intagral.MainMenuActivity
import com.ssafy.intagral.R
import com.ssafy.intagral.data.ProfileDetail
import com.ssafy.intagral.data.ProfileSimpleItem
import com.ssafy.intagral.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: 뒤로가기 버튼 만들기
        binding = ActivitySearchBinding.inflate(layoutInflater).apply {
            searchFragment.setOnQueryTextListener(SearchListener())
            searchFragment.requestFocus()
            supportFragmentManager.beginTransaction().replace(R.id.search_result_profile_simple, ProfileSimpleListFragment()).commit()
        }
        setContentView(binding.root)
    }

    inner class SearchListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
            supportFragmentManager.setFragmentResult("search text", bundleOf("input text" to query))
            return true
        }
        override fun onQueryTextChange(newText: String?): Boolean {
            supportFragmentManager.setFragmentResult("search text", bundleOf("input text" to newText))
            return true
        }
    }

    fun changeActivity(index: Int, profileSimple: ProfileSimpleItem) {
        when(index) {
            1 -> {
                var intent = Intent(this@SearchActivity, MainMenuActivity::class.java)
                intent.putExtra("profileSimple", profileSimple)
                setResult(RESULT_OK, intent)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
                finish()
            }
        }
    }
}