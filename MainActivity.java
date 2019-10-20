/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match the package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContextView;

import android.app.ActionBar;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    int Price = 5;
    boolean haschoco;
    boolean withcream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Toast pops up
            Toast.makeText(this, "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //a toast pops up
            Toast.makeText(this, "You cannot order less than a Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This is the functioning of CalculatePrice function
     */
    private int calculatePrice(boolean withcream, boolean hasChoco) {
        int base_price = 5;
        /**
         * Price adjustments when cream is chosen.
         */
        if (withcream) {
            base_price += 1;
        }
        /**
         * Price adjustments when chocolate is chosen.
         */
        if (hasChoco) {
            base_price += 2;
        }
        return base_price * quantity;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        /**
         * Check if user wants Whipped Cream
         */
        CheckBox cream_CheckBox = (CheckBox) findViewById(R.id.cream_checkboxe);

        /**
         * Declaration of choco variable as boolean
         */
        boolean withcream = cream_CheckBox.isChecked();

        /**
         * Check if user wants chocolate shavings
         */
        CheckBox choco_Checkbox = (CheckBox) findViewById(R.id.chocolate_wala_box);

        /**
         * Declaration of choco variable as boolean
         */
        boolean hasChoco = choco_Checkbox.isChecked();

        /**
         * obtaining name of the consumer from Resource id.
         */
        EditText name = (EditText) findViewById(R.id.hint_hai);
        String naam = name.getText().toString();

        /**
         * Declaration of price variable
         */
        int Price = calculatePrice(withcream, hasChoco);
        String priceMessage = createOrderSummary(Price, withcream, hasChoco, naam);

        /**
         * Redirection from JustJava app to a valid emailing app in device
         */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                "Coffee Order from Just Java, only for " + naam);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the price value on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * Create summary of the order
     *
     * @param price     is the price of the order
     * @param withcream shows whether cream is chosen or not
     * @param hasChoco  displays whether chocolate shavings are selected or not
     * @return shows the final content to be displayed on the screen
     */
    private String createOrderSummary(int price, boolean withcream, boolean hasChoco, String naam) {
        String priceMessage = naam;
        priceMessage += "\nAdd Chocolate Shavings? " + hasChoco;
        priceMessage += "\nAdd Whipped Cream? " + withcream;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: " + price;
        priceMessage += "\nWith Love!!!!";
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }

}
