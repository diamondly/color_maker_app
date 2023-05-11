package edu.fullerton.fz.persistencecolor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import kotlin.math.roundToInt

const val LOG_TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var redSeek: SeekBar
    lateinit var redView: EditText
    private lateinit var redSwitch: Switch
    private lateinit var greenSeek: SeekBar
    lateinit var greenView: EditText
    private lateinit var greenSwitch: Switch
    private lateinit var blueSeek: SeekBar
    lateinit var blueView: EditText
    private lateinit var blueSwitch: Switch
    private lateinit var colorBox: Button
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button

    private val myViewModel: MyViewModel by lazy {
        MyPreferencesRepo.initialize(this)
        MyViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            myViewModel.saveInput(redView.text.toString(), "red")
            myViewModel.saveInput(greenView.text.toString(), "green")
            myViewModel.saveInput(blueView.text.toString(), "blue")
        }

        var red = 0
        var temp = 0
        var green = 0
        var blue = 0

        colorBox = this.findViewById(R.id.colorBox)
        resetButton = this.findViewById(R.id.resetButton)

        colorBox.setOnLongClickListener(colorBoxLongClickListener)

        redSeek = this.findViewById(R.id.redSeek)
        redView = this.findViewById(R.id.redView)
        redSwitch = this.findViewById(R.id.redSwitch)

        redSwitch.setOnCheckedChangeListener { p0, p1 ->
            if (!p1) {
                redSeek.isEnabled = false
                temp = red
                red = 0
            } else {
                redSeek.isEnabled = true
                redView.isEnabled = true
                red = temp
            }
            colorBox.setBackgroundColor(Color.argb(255, red, green, blue))
            colorBox.text = "RGB: $red, $green, $blue"
        }

        redSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(red_sb: SeekBar?, p1: Int, p2: Boolean) {
                red = p1
                temp = p1
                redView.setText("%.3f".format(red/255.00))
                colorBox.setBackgroundColor(Color.argb(255, red, green, blue))
                colorBox.text = "RGB: $red, $green, $blue"
                println("RED SeekBar value has changed ${red_sb?.progress}")
            }
            override fun onStartTrackingTouch(sb: SeekBar?) { }
            override fun onStopTrackingTouch(sb: SeekBar?) { }
        })

        redView.setOnClickListener{ redView.setText("") }

        redView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s)) {
                    redSeek.progress = 0
                }
                else {
                    val rValue = s.toString().toDouble()
                    if (s.toString().length <= 2 && rValue == 1.00) {
                        redView.setText("1.000")
                    }
                    else if (s.toString().length == 5 && rValue >= 0 && rValue <= 1) {
                        red = (rValue * 255).toInt()
                        redSeek.progress = red
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int,  count: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int,  count: Int) { }
        })

        greenSeek = this.findViewById(R.id.greenSeek)
        greenView = this.findViewById(R.id.greenView)
        greenSwitch = this.findViewById(R.id.greenSwitch)

        greenSwitch.setOnCheckedChangeListener { p0, p1 ->
            if (!p1) {
                greenSeek.isEnabled = false
                temp = green
                green = 0
            } else {
                greenSeek.isEnabled = true
                greenView.isEnabled = true
                green = temp
            }
            colorBox.setBackgroundColor(Color.argb(255, red, green, blue))
            colorBox.text = "RGB: $red, $green, $blue"
        }

        greenSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(green_sb: SeekBar?, p1: Int, p2: Boolean) {
                green = p1
                temp = p1
                greenView.setText("%.3f".format(green / 255.00))
                colorBox.setBackgroundColor(Color.argb(255, red, green, blue))
                colorBox.text = "RGB: $red, $green, $blue"
                println("GREEN SeekBar value has changed ${green_sb?.progress}")
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        greenView.setOnClickListener{ greenView.setText("") }

        greenView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s)) {
                    greenSeek.progress = 0
                } else {
                    val gValue = s.toString().toDouble()
                    if (s.toString().length <= 2 && gValue == 1.00) {
                        greenView.setText("1.000")
                    } else if (s.toString().length == 5 && gValue >= 0 && gValue <= 1) {
                        green = (gValue * 255).toInt()
                        greenSeek.progress = green
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        blueSeek = this.findViewById(R.id.blueSeek)
        blueView = this.findViewById(R.id.blueView)
        blueSwitch = this.findViewById(R.id.blueSwitch)

        blueSwitch.setOnCheckedChangeListener { p0, p1 ->
            if (!p1) {
                blueSeek.isEnabled = false
                temp = blue
                blue = 0
            } else {
                blueSeek.isEnabled = true
                blueView.isEnabled = true
                blue = temp
            }
            colorBox.setBackgroundColor(Color.argb(255, red, green, blue))
            colorBox.text = "RGB: $red, $green, $blue"
        }

        blueSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(blue_sb: SeekBar?, p1: Int, p2: Boolean) {
                blue = p1
                temp = p1
                blueView.setText("%.3f".format(blue / 255.00))
                colorBox.setBackgroundColor(Color.argb(255, red, green, blue))
                colorBox.text = "RGB: $red, $green, $blue"
                println("BLUE SeekBar value has changed ${blue_sb?.progress}")
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        blueView.setOnClickListener{ blueView.setText("") }

        blueView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s)) {
                    blueSeek.progress = 0
                } else {
                    val bValue = s.toString().toDouble()
                    if (s.toString().length <= 2 && bValue == 1.00) {
                        blueView.setText("1.000")
                    } else if (s.toString().length == 5 && bValue >= 0 && bValue <= 1) {
                        blue = (bValue * 255).toInt()
                        blueSeek.progress = blue
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        resetButton.setOnClickListener {
            red = 0
            redView.setText("%.3f".format(red / 255.00))
            redView.isEnabled = false
            redSeek.progress = 0
            redSwitch.isChecked = false

            green = 0
            greenView.setText("%.3f".format(green / 255.00))
            greenView.isEnabled = false
            greenSeek.progress = 0
            greenSwitch.isChecked = false

            blue = 0
            blueView.setText("%.3f".format(blue / 255.00))
            blueView.isEnabled = false
            blueSeek.progress = 0
            blueSwitch.isChecked = false

            colorBox.setBackgroundColor(Color.argb(255, red, green, blue))
            colorBox.text = "RGB: $red, $green, $blue"
            Toast.makeText(this, R.string.reset_msg, Toast.LENGTH_SHORT).show()
        }

        this.myViewModel.loadInputs(this)
        Log.d(LOG_TAG, "Loaded in RGB values")
    }

    private val colorBoxLongClickListener = View.OnLongClickListener {
        val rgbValues = colorBox.text
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", rgbValues)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "RGB values copied to clipboard", Toast.LENGTH_LONG).show()
        true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "RGB values saved")
    }

}