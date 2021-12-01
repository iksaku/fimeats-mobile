package com.equipo2.fimeats.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.cardform.view.CardForm
import com.equipo2.fimeats.R
import com.equipo2.fimeats.models.Product
import java.util.*


class PaymentActivity : AppCompatActivity() {
    var alertBuilder: AlertDialog.Builder? = null
    lateinit var product: Product;
    private var tvProductNameResume: TextView? = null
    private var tvTotal: TextView? = null
    private var ivProductImageResume: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val extras: Bundle? = intent.extras
        if (extras != null) {
            product = extras.getSerializable("product")!! as Product
        }

        tvProductNameResume = findViewById(R.id.tvProductNameResume)
        tvTotal = findViewById(R.id.tvProductTotal)
        ivProductImageResume = findViewById(R.id.ivProductImageResume)

        tvProductNameResume?.text = product.name
        tvTotal?.text = "Total: $${String.format("%.2f", product.price)}"

        var idImage: Int? = null
        if (product.image.isNotEmpty()) {
            idImage = this.resources.getIdentifier(
                product.image.substringBeforeLast('.')
                    .lowercase(Locale.getDefault()).replace('-', '_'), "drawable", this.packageName)
        }

        ivProductImageResume?.setImageResource(idImage ?: R.drawable.food_placeholder)

        val cardForm: CardForm = findViewById(R.id.card_form)
        val buy: Button = findViewById(R.id.btnBuy)

        cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .postalCodeRequired(true)
            .mobileNumberRequired(true)
            .mobileNumberExplanation("")
            .setup(this@PaymentActivity)
        cardForm.cvvEditText.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        buy.setOnClickListener {
            if (cardForm.isValid) {
                alertBuilder = AlertDialog.Builder(this@PaymentActivity)
                alertBuilder?.setTitle("Confirmación de compra")
                alertBuilder?.setMessage(
                    """
                        Numero de tarjeta: ${cardForm.cardNumber}
                        Expiración: ${cardForm.expirationDateEditText.text.toString()}
                        CVV: ${cardForm.cvv}
                        Codigo postal: ${cardForm.postalCode}
                        Telefono: ${cardForm.mobileNumber}
                        """.trimIndent()
                )
                alertBuilder!!.setPositiveButton("Confirmar",
                    DialogInterface.OnClickListener { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        Toast.makeText(
                            this@PaymentActivity,
                            "Gracias por su compra",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    })
                alertBuilder!!.setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
                val alertDialog: AlertDialog = alertBuilder!!.create()
                alertDialog.show()
            } else {
                Toast.makeText(this@PaymentActivity, "Por favor, complete todos los campos", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}