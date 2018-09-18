package net.codejava.javaee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/result.html")
public class JavaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JavaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		int TB = 4118; //Базовая максимальная тарифная ставка 
		double KT = 0;  //Коэффициент территории
		double KBM = 1; //Коэффициент Бонус-Малус, по умолчанию 3 (1)
		double KVS = 0; //Коэффициент Возраст-стаж (Вычисляется 
		double KOMultidrive = 1.8; //Для мультидрайва
		double KM = 0; //Коэффициент мощности
		double CalculatePrem; //Переменная для расчета страховой премии
		String ResultHtml = "<!DOCTYPE html>  \r\n" +
				"<html>\r\n" +
				"<head>\r\n" +
				"<meta charset=\"UTF-8\">\r\n" +
				"<link rel = \"stylesheet\" href = \"style.css\">\r\n" +
				"<link href=\"https://fonts.googleapis.com/css?family=Roboto+Slab\" rel=\"stylesheet\">\r\n" +
				"<title>Результат</title>\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				"<div class=forma>";
		PrintWriter out = response.getWriter(); //Создаем копию экземпляра класса для вывода текста
		int City = Integer.parseInt(request.getParameter("City"));
		int Power = Integer.parseInt(request.getParameter("Power"));
		int Age = Integer.parseInt(request.getParameter("Age"));
		int Experience = Integer.parseInt(request.getParameter("Experience"));
		if (City == 1) KT = 2;
		if (City == 2 || City == 3) KT = 1.8;
		{
			if ((Power > 0 & Age > 0 & Experience >= 0) & (Age >= Experience + 18)) // Проверка на ввод валидных значений для расчета
			{
				if (Power <= 50) KM = 0.6;
				if (Power > 50 & Power <= 70) KM = 1;
				if (Power > 70 & Power <= 100) KM = 1.1;
				if (Power > 100 & Power <= 120) KM = 1.2;
				if (Power > 120 & Power <= 150) KM = 1.4;
				if (Power > 150) KM = 1.6;
				if (Age <= 22 & Experience <= 3) KVS = 1.8;
				if (Age > 22 & Experience <= 3) KVS = 1.7;
				if (Age < 22 & Experience > 3) KVS = 1.6;
				if (Age > 22 & Experience > 3) KVS = 1;
				CalculatePrem = TB * KT * KBM * KVS * KM; //расчет страховой премии
				CalculatePrem = Math.rint(100.0 * CalculatePrem) / 100.0; //оставляем два знака после запятой
				out.println(ResultHtml + "<h1>Стоимость ОСАГО:  " + CalculatePrem + " руб.</h1><h2>Использованные коэффициенты</h2><h3>TБ (Базовый тариф) =" + TB + "</h3><h3>KT (Коэффициент территории преимущественного использования) =" + KT + "</h3><h3>KБM (Коэффициент 'бонус-малус') =" + KBM + "</h3><h3>КВС (Коэффициент в зависимости от возраста и стажа водителя) =" + KVS + "</h3><h3>КМ (Коэффициент технических характристик ТС) =" + KM + "</h3></div>");
			} else
				out.println(ResultHtml + "<h1>Ошибка. Валидные данные для расчета - Возраст водителя больше стажа на 18 лет.</h1>"); //эксепшн на ввод невалидных данных
		}
	}
}

