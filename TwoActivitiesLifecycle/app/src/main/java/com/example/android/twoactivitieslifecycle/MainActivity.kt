/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.twoactivitieslifecycle

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

/**
 * The TwoActivities app contains two activities and sends messages (intents) between them.
 */
class MainActivity : AppCompatActivity() {

    // EditText view for the message
    private var mMessageEditText: EditText? = null
    // TextView for the reply header
    private var mReplyHeadTextView: TextView? = null
    // TextView for the reply body
    private var mReplyTextView: TextView? = null

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")

        // Initialize all the view variables.
        mMessageEditText = findViewById<View>(R.id.editText_main) as EditText
        mReplyHeadTextView = findViewById<View>(R.id.text_header_reply) as TextView
        mReplyTextView = findViewById<View>(R.id.text_message_reply) as TextView

        // Restore the saved state. See onSaveInstanceState() for what gets saved.
        if (savedInstanceState != null) {
            val isVisible = savedInstanceState.getBoolean("reply_visible")
            // Show both the header and the message views.  If isVisible is
            // false or missing from the bundle, use the default layout.
            if (isVisible) {
                mReplyHeadTextView!!.visibility = View.VISIBLE

                mReplyTextView!!.text = savedInstanceState.getString("reply_text")
                mReplyTextView!!.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Handle the onClick for the "Send" button. Gets the value of the main EditText,
     * creates an intent, and launches the second activity with that intent.
     *
     * The return intent from the second activity is onActivityResult().
     * @param view The view (Button) that was clicked.
     */
    fun launchSecondActivity(view: View) {
        Log.d(LOG_TAG, "Button clicked!")

        val intent = Intent(this, SecondActivity::class.java)
        val message = mMessageEditText!!.text.toString()

        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    /**
     * Handle the data in the return intent from SecondActivity.
     *
     * @param requestCode Code for the SecondActivity request.
     * @param resultCode Code that comes back from SecondActivity.
     * @param data Intent data sent back from SecondActivity.
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Test for the right intent reply
        if (requestCode == TEXT_REQUEST) {
            // Test to make sure the intent reply result was good.
            if (resultCode == Activity.RESULT_OK) {
                val reply = data.getStringExtra(SecondActivity.EXTRA_REPLY)

                // Make the reply head visible.
                mReplyHeadTextView!!.visibility = View.VISIBLE

                // Set the reply and make it visible.
                mReplyTextView!!.text = reply
                mReplyTextView!!.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Lifecycle callback for start.
     */
    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart")
    }

    /**
     * Lifecycle callback for restart.
     */
    public override fun onRestart() {
        super.onRestart()
        Log.d(LOG_TAG, "onRestart")
    }

    /**
     * Lifecycle callback for resume.
     */
    public override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume")
    }

    /**
     * Lifecycle callback for pause.
     */
    public override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause")
    }

    /**
     * Lifecycle callback for stop.
     */
    public override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop")
    }

    /**
     * Lifecycle callback for destroy.
     */
    public override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy")
    }

    /**
     * Save the instance state if the activity is restarted (for example, on device rotation.)
     * Here you save the contents of the reply text, and whether the reply is visible.
     *
     * @param outState The state data.
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        // If the heading is visible, we have a message that needs to be saved.
        // Otherwise we're still using default layout.
        if (mReplyHeadTextView!!.visibility == View.VISIBLE) {
            outState!!.putBoolean("reply_visible", true)
            outState.putString("reply_text", mReplyTextView!!.text.toString())
        }

    }

    companion object {
        // Class name for Log tag
        private val LOG_TAG = MainActivity::class.java.simpleName
        // Unique tag required for the intent extra
        val EXTRA_MESSAGE = "com.example.android.twoactivitieslifecycle.extra.MESSAGE"
        // Unique tag for the intent reply
        val TEXT_REQUEST = 1
    }
}
