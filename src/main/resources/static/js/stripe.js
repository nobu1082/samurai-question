 const stripe = Stripe('pk_test_51OLEXaJsSQOmZTMCCtIZstRDsg1FuX1FZkhHP66OszEnfqiJYkraKvjf6btmDUH7qsg9Rnah2euJMamhDMHGB7L4008PCwqVgC');
 const paymentButton = document.querySelector('#paymentButton');
 
 paymentButton.addEventListener('click', () => {
   stripe.redirectToCheckout({
     sessionId: sessionId
   })
 });