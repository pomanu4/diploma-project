package ua.com.company.components;

import java.util.Collection;
import org.springframework.stereotype.Component;

import ua.com.company.entity.SimpleOrder;

@Component
public class TotalSumCalculator {

	public int findSum(Collection<SimpleOrder> coll) {
		int summ = 0;

		for (SimpleOrder simOrd : coll) {
			summ = summ + (simOrd.getHowMany() * simOrd.getProduct().getPrice());
		}
		return summ;
	}

}
