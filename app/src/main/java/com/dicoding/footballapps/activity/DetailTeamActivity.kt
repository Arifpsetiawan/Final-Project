package com.dicoding.footballapps.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.*

class DetailTeamActivity : AppCompatActivity() {

    companion object {
        const val TEAM_ID                = "id"
        const val TEAM_NAME              = "name"
        const val TEAM_IMAGE             = "image"
        const val TEAM_DESCRIPTION       = "description"
    }
    private var team_id:             String  = ""
    private var team_name:           String  = ""
    private var team_logo:          Int     = 0
    private var team_description:    String  = ""

    lateinit var name_textView    : TextView
    lateinit var image_imageView   : ImageView
    lateinit var desc_textView   : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            padding = dip(18)

            image_imageView = imageView()
                .lparams(width = dip(100),
                    height = wrapContent
                ) {
                    gravity = Gravity.CENTER
                }

            name_textView = textView()
                .lparams(width = wrapContent) {
                    gravity = Gravity.CENTER
                    topMargin = dip(12)
                }

            desc_textView = textView()
                .lparams(width = wrapContent) {
                    margin = dip(20)

                }
        }

        team_id          = intent.getStringExtra(TEAM_ID)
        name_textView.text = team_name
        Glide.with(image_imageView).load(team_logo).into(image_imageView)
        desc_textView.text = team_description

    }
}