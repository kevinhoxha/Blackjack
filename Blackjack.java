import java.util.*;

public class Blackjack
{
	private static String[] cards =
	{ "Ace of Spades", "2 of Spades", "3 of Spades", "4 of Spades", "5 of Spades", "6 of Spades", "7 of Spades",
			"8 of Spades", "9 of Spades", "10 of Spades", "Jack of Spades", "Queen of Spades", "King of Spades",
			"Ace of Hearts", "2 of Hearts", "3 of Hearts", "4 of Hearts", "5 of Hearts", "6 of Hearts", "7 of Hearts",
			"8 of Hearts", "9 of Hearts", "10 of Hearts", "Jack of Hearts", "Queen of Hearts", "King of Hearts",
			"Ace of Clubs", "2 of Clubs", "3 of Clubs", "4 of Clubs", "5 of Clubs", "6 of Clubs", "7 of Clubs",
			"8 of Clubs", "9 of Clubs", "10 of Clubs", "Jack of Clubs", "Queen of Clubs", "King of Clubs",
			"Ace of Diamonds", "2 of Diamonds", "3 of Diamonds", "4 of Diamonds", "5 of Diamonds", "6 of Diamonds",
			"7 of Diamonds", "8 of Diamonds", "9 of Diamonds", "10 of Diamonds", "Jack of Diamonds",
			"Queen of Diamonds", "King of Diamonds" };

	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Would you like to play Blackjack? (Y/N)");
		String yesOrNo = s.next();
		int winCount = 0;
		int pushCount = 0;
		int lossCount = 0;
		while (yesOrNo.toLowerCase().equals("y"))
		{
			int match = playMatch(s);
			if (match == 0)
			{
				winCount++;
			} else if (match == 1)
			{
				pushCount++;
			} else if (match == 2)
			{
				lossCount++;
			}
			System.out
					.println("You have " + winCount + " wins, " + pushCount + " pushes, and " + lossCount + " losses.");
			System.out.println("Would you like to play again? (Y/N)");
			yesOrNo = s.next();
		}
		System.out.println("Ok, have a good day!");
		s.close();
	}

	public static int playMatch(Scanner s)
	{
		List<String> cardList = new ArrayList<>(Arrays.asList(cards));
		HashMap<String, Integer> dealerCards = new HashMap<>();
		HashMap<String, Integer> playerCards = new HashMap<>();
		for (int i = 0; i < 2; i++)
		{
			int num1 = (int) (Math.random() * cardList.size());
			dealerCards.put(cardList.get(num1), singleValue(cardList.get(num1)));
			cardList.remove(num1);

			int num2 = (int) (Math.random() * cardList.size());
			playerCards.put(cardList.get(num2), singleValue(cardList.get(num2)));
			cardList.remove(num2);
		}
		String hitOrStand = "";
		System.out.println("Your cards are: " + cardsToString(playerCards.keySet().toArray()));
		System.out.println("The value of your cards is: " + calculateValue(playerCards));
		do
		{
			System.out.println("The dealer is showing: " + dealerCards.keySet().toArray()[0]);
			System.out.println("Do you want to hit or stand? (H/S)");
			hitOrStand = s.next();
			if (hitOrStand.toLowerCase().equals("h"))
			{
				int rand = (int) (Math.random() * cardList.size());
				playerCards.put(cardList.get(rand), singleValue(cardList.get(rand)));
				cardList.remove(rand);
				System.out.println("Your cards are: " + cardsToString(playerCards.keySet().toArray()));
				System.out.println("The value of your cards is: " + calculateValue(playerCards));
			}
		} while (calculateValue(playerCards) <= 21 && !hitOrStand.toLowerCase().equals("s"));

		if (calculateValue(playerCards) > 21)
		{
			System.out.println("Sorry, you busted.");
			return 2;
		} else
		{
			System.out.println("The dealer had: " + cardsToString(dealerCards.keySet().toArray()));
			while (calculateValue(dealerCards) < 17)
			{
				int rand = (int) (Math.random() * cardList.size());
				dealerCards.put(cardList.get(rand), singleValue(cardList.get(rand)));
				cardList.remove(rand);
				System.out.println("The dealer hits: " + cardsToString(dealerCards.keySet().toArray()));
			}
			System.out.println("The value of your cards is: " + calculateValue(playerCards));
			System.out.println("The value of the dealer's cards is: " + calculateValue(dealerCards));
			if (calculateValue(dealerCards) > 21)
			{
				System.out.println("The dealer busted. Congratulations, you win!");
				return 0;
			} else if (calculateValue(playerCards) > calculateValue(dealerCards))
			{
				System.out.println("Congratulations, you won!");
				return 0;
			} else if (calculateValue(playerCards) == calculateValue(dealerCards))
			{
				System.out.println("You pushed with the dealer.");
				return 1;
			} else
			{
				System.out.println("Sorry, you lost.");
				return 2;
			}
		}
	}

	public static int calculateValue(HashMap<String, Integer> cards)
	{
		int value = 0;
		for (int i = 0; i < cards.size(); i++)
		{
			value += cards.get(cards.keySet().toArray()[i]);
		}
		for (int i = 0; i < containsAce(cards.keySet().toArray()); i++)
		{
			if (value > 21)
			{
				value -= 10;
			}
		}
		return value;
	}

	public static int singleValue(String card)
	{
		int value = 0;
		if (card.split(" ")[0].equals("Ace"))
		{
			value = 11;
		} else if (card.split(" ")[0].equals("Jack") || card.split(" ")[0].equals("Queen")
				|| card.split(" ")[0].equals("King"))
		{
			value = 10;
		} else
		{
			value = Integer.parseInt(card.split(" ")[0]);
		}
		return value;
	}

	public static String cardsToString(Object[] cards)
	{
		String ret = "";
		if (cards.length == 1)
		{
			ret = cards[0].toString();
		}
		if (cards.length == 2)
		{
			ret = cards[0].toString() + " and " + cards[1].toString();
		} else
		{
			for (int i = 0; i < cards.length - 1; i++)
			{
				ret += cards[i].toString() + ", ";
			}
			ret += "and " + cards[cards.length - 1].toString();
		}
		return ret;
	}

	public static int containsAce(Object[] cards)
	{
		int num = 0;
		for (Object card : cards)
		{
			if (card.toString().split(" ")[0].equals("Ace"))
			{
				num++;
			}
		}
		return num;
	}
}