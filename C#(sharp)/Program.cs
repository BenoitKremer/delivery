using System;

    // Références:
    // in = lecture seule
    // ref = lecture + écriture
    // out = déclaration de l'extérieur



class Program
{
    static void Main(string[] args)
    {
        int number = 5;
        int number2 = 10;

        Console.WriteLine(number);
        Console.WriteLine(number2);

        ResetNumbers(number, ref number2);

        Console.WriteLine(number);
        Console.WriteLine(number2);
    }
    public static void ResetNumbers(in int nb, ref int nb2)
    {
        Console.WriteLine(nb);
        nb2 = 0;
    }
}