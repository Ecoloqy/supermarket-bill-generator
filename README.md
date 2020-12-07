# Supermarket bill generator
I'm sure you have done shopping in a supermarket and you have been looking for discounts, didn't you? But have you ever thought how those discounts are calculated? Now is the time to implement such mechanism.

Your task is to implement an algorithm which would calculate the total price for a given basket.

Data
Miss Smith from Price department will provide you a csv file with product prices including the discounts. Samle file looks like this:

barcode, name, amount, price
1001, beer, 1, 1.20
1001, beer, 2, 2.00
1243, eggs, 1, 0.20
3401, chocolate, 1, 3.15
1243, eggs, 10, 1.90
As you can see the prices are defined for a given amount. One beer costs 1.20 EUR but if we buy two we pay 2 EUR in total. If we buy three we pay 3.20 EUR (2+1).

Barcode scanner at cash desk gives us a text file with barcodes. One file represents one basket. It looks like this:

1001
1001
3401
1001
3401
3401
3401
1001
1243
1243
Have in mind that products are not grouped, they are listed in order that the client put them at the cash desk.

Task
So your task is to design and implement such system. You don't have to use any database. You can store the data in-memory. Use TDD methodology in this project.

Sample usage:

$>java GenerateBill product_prices.csv basket.txt
The total price is: 17.00 EUR
Too easy?
If this exercise was too easy for you than consider a situation when discounts could be calculated in a more complex way e.g: you get a 50% discount for a one beer if you buy three eggs. Think how to model product_prices.csv in such situtation. Then re-implement your program.
