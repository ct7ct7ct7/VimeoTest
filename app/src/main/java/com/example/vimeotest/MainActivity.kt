package com.example.vimeotest

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ct7ct7ct7.androidvimeoplayer.model.PlayerState
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var REQUEST_CODE = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(vimeoPlayer)
        vimeoPlayer.initialize(59777392)

        vimeoPlayer.setFullscreenClickListener {
            //define the orientation
            var requestOrientation = VimeoPlayerActivity.REQUEST_ORIENTATION_AUTO
            startActivityForResult(VimeoPlayerActivity.createIntent(this, requestOrientation, vimeoPlayer), REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            var playAt = data!!.getFloatExtra(VimeoPlayerActivity.RESULT_STATE_VIDEO_PLAY_AT, 0f)
            vimeoPlayer.seekTo(playAt)

            var playerState = PlayerState.valueOf(data!!.getStringExtra(VimeoPlayerActivity.RESULT_STATE_PLAYER_STATE))
            when (playerState) {
                PlayerState.PLAYING -> vimeoPlayer.play()
                PlayerState.PAUSED -> vimeoPlayer.pause()
            }
        }
    }

}
