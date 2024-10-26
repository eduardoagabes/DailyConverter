package com.eduardoagabes.dailyconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eduardoagabes.dailyconverter.databinding.ActivityResultBinding
import com.google.android.material.snackbar.Snackbar

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvResult = binding.tvResult
        val tieWeight = binding.tieWeigth
        val tieDistance = binding.tieDistance
        val tieVolume= binding.tieVolume

        ArrayAdapter.createFromResource(
            this,
            R.array.weight_array,
            R.layout.spinner_color
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.my_dropdown_item_color)
            binding.spinnerWeight1.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.weight_array,
            R.layout.spinner_color
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.my_dropdown_item_color)
            binding.spinnerWeight2.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.distance_array,
            R.layout.spinner_color
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.my_dropdown_item_color)
            binding.spinnerDistance1.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.distance_array,
            R.layout.spinner_color
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.my_dropdown_item_color)
            binding.spinnerDistance2.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.volume_array,
            R.layout.spinner_color
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.my_dropdown_item_color)
            binding.spinnerVolume1.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.volume_array,
            R.layout.spinner_color
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.my_dropdown_item_color)
            binding.spinnerVolume2.adapter = adapter
        }

        binding.btnConverter.setOnClickListener {
            val inputWeight = tieWeight.text.toString()
            val inputDistance = tieDistance.text.toString()
            val inputVolume = tieVolume.text.toString()


            if (inputWeight.isEmpty() && inputDistance.isEmpty() && inputVolume.isEmpty()) {
                Snackbar.make(
                    tvResult,
                    "Todos los campos están vacíos",
                    Snackbar.LENGTH_LONG
                ).show()

            } else if(inputWeight.isNotEmpty()) {
                val fromWeightUnit = binding.spinnerWeight1.selectedItem.toString()
                val toWeightUnit = binding.spinnerWeight2.selectedItem.toString()
                val weightValue = inputWeight.toDouble()
                val weightResult = convertWeight(weightValue, fromWeightUnit, toWeightUnit)
                tvResult.text = "$weightResult"
            }

            if (inputDistance.isNotEmpty()) {
                val fromDistanceUnit = binding.spinnerDistance1.selectedItem.toString()
                val toDistanceUnit = binding.spinnerDistance2.selectedItem.toString()
                val distanceValue = inputDistance.toDouble()
                val distanceResult = convertDistance(distanceValue, fromDistanceUnit, toDistanceUnit)
                tvResult.text = "$distanceResult"
            }

            if (inputVolume.isNotEmpty()) {
                val fromVolumeUnit = binding.spinnerVolume1.selectedItem.toString()
                val toVolumeUnit = binding.spinnerVolume2.selectedItem.toString()
                val volumeValue = inputVolume.toDouble()
                val volumeResult = convertVolume(volumeValue, fromVolumeUnit, toVolumeUnit)
                tvResult.text = "$volumeResult"
            }
        }

        binding.btnClean.setOnClickListener {

            tieWeight.setText("")
            tieDistance.setText("")
            tieVolume.setText("")

            binding.spinnerWeight1.setSelection(0)
            binding.spinnerWeight2.setSelection(0)
            binding.spinnerDistance1.setSelection(0)
            binding.spinnerDistance2.setSelection(0)
            binding.spinnerVolume1.setSelection(0)
            binding.spinnerVolume2.setSelection(0)

            tvResult.text = ""
        }
    }

    private fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {
        val baseValue = when (fromUnit) {
            "Kilogramos" -> value
            "Gramos" -> value / 1000
            "Miligramos" -> value / 1_000_000
            "Toneladas" -> value * 1000
            "Libras" -> value / 2.20462
            else -> value
        }

        return when (toUnit) {
            "Kilogramos" -> baseValue
            "Gramos" -> baseValue * 1000
            "Miligramos" -> baseValue * 1_000_000
            "Toneladas" -> baseValue / 1000
            "Libras" -> baseValue * 2.20462
            else -> baseValue
        }
    }

    private fun convertDistance(value: Double, fromUnit: String, toUnit: String): Double {
        val baseValue = when (fromUnit) {
            "Metros" -> value
            "Centímetros" -> value / 100
            "Milímetros" -> value / 1000
            "Quilómetros" -> value * 1000
            "Milhas" -> value * 1609.34
            else -> value
        }

        return when (toUnit) {
            "Metros" -> baseValue
            "Centímetros" -> baseValue * 100
            "Milímetros" -> baseValue * 1000
            "Quilómetros" -> baseValue / 1000
            "Milhas" -> baseValue / 1609.34
            else -> baseValue
        }
    }

    private fun convertVolume(value: Double, fromUnit: String, toUnit: String): Double {
        val baseValue = when (fromUnit) {
            "Litros" -> value
            "Mililitros" -> value / 1000
            "Metros cúbicos" -> value * 1000
            "Centímetros cúbicos" -> value / 1000
            "Galões" -> value * 3.78541
            "Onças fluídas" -> value / 33.814
            "Barris" -> value * 158.987
            else -> value
        }

        return when (toUnit) {
            "Litros" -> baseValue
            "Mililitros" -> baseValue * 1000
            "Metros cúbicos" -> baseValue / 1000
            "Centímetros cúbicos" -> baseValue * 1000
            "Galones" -> baseValue / 3.78541
            else -> baseValue
        }
    }