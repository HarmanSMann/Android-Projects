<?php
require 'vendor/autoload.php';
//making an auth key check
if (isset($_POST['authKey']) && ($_POST['authKey'] == "my value")) {

$stripe = new \Stripe\StripeClient('SECRET KEY HERE');

$customer = $stripe->customers->create(
    [
        'name' => "Harman"
        'address' => [
            'line1' => 'Demo address',
            'postal_code' => '123456',
            'city' => 'Demo',
            'state' => 'DO',
            'country' => 'AS',
        ]
    ]
);
$ephemeralKey = $stripe->ephemeralKeys->create([
  'customer' => $customer->id,
], [
  'stripe_version' => '2022-08-01',
]);
$paymentIntent = $stripe->paymentIntents->create([
  'amount' => 15 * 100, //this would be $15.00
  'currency' => 'cad',
  'description' => 'Payment for the Demo',
  'customer' => $customer->id,
  // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
  'automatic_payment_methods' => [
    'enabled' => 'true',
  ],
]);

echo json_encode(
  [
    'paymentIntent' => $paymentIntent->client_secret,
    'ephemeralKey' => $ephemeralKey->secret,
    'customer' => $customer->id,
    'publishableKey' => 'public key from the Stripe account'
  ]
);
http_response_code(200);
} echo "not authorised "