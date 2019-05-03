package ru.stqa.pft.sandbox;

public class Program1 {

	public static void main(String[] args) {
		hello("world");
		hello("user");
		hello("Aleksandr");

		Square s = new Square(5);
		System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

		Rectangle r = new Rectangle(4, 6);
		System.out.println("Площадь прямоугольника со стороной " + r.a + " и " + r.b + " = " + r.area());
	}

	public static void hello (String name) {
		System.out.println("Hello, " + name + "!");
	}
}
