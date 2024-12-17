public class Factory {
	public static Calculator createCaculator(String start, String destination, int mode, Grapher grapher)
	{
		Calculator calculator = null;
		switch(mode)
		{
			case 1:
				calculator = new SavingMoney(start, destination, mode, grapher);
				break;
			case 2:
				calculator = new SavingTime(start, destination, mode, grapher);
				break;
		}
		return calculator;
	}
}
