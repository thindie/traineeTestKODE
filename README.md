# traineeTestKODE

kode android trainee test

stack: kotlin, compose, retrofit, coroutines, mvvm, hilt, coil

1.
2. decomposition


	2 часа
	
	1) подготовка среды - настройка gradle, 
		git init
		старт data-слоя: 
		repository, retrofit.
		определение моделей, мапперов

			ожидалось - 2 часа, фактически - Took 39 minutes

	3) прокидывание di, ручной тест прилета данных из сети

 			ожидалось - 2 часа, фактически - Took 1 hour 25 minutes



	3) верстка searchBar 
	   верстка tabSelector

			ожидалось - 2 часа, фактически -    1 hour 15 minutes

	4) верстка скелетона

			ожидалось - 2 часа, фактически -    10 minutes (заюзал из прошлых pet)
	
	5) верстка listUnit

			ожидалось - 2 часа, фактически - Took 1 hour 58 minutes

	7) импорт необходимых иконок из фигма + FONTS

			Took 10 minutes + Took 53 minutes --- шрифты забыл и не декомпозировал!!

 
	9) !!	секция custom notification bar
		4 часа: поиск и изучение теории
		2 часа + n: имплементация
		временные затраты не ясны, ввиду незнакомой технологии

			по нотификейшен бару - оставил базовый андроид, ввиду того, что прочитал доку на developers.
			 этот момент скользкий и его надо обсуждать со старшими кодерами / дизайнерами / etc
   			однако я приготовил api на 80%, его можно заюзать в тестовом режиме - будет выглядеть как на дизайн-проекте в фигма
			!!затрачено около 6 часов в сумме.
			

	
	10) !!   cекция composebottom sheet
		
		4 часа: поиск и изучение теории
		4 часа + n: имплементация
		временные затраты не ясны, ввиду незнакомой технологии

			по Bottom sheet все получилось гораздо более интереснее -
			Took 2 hours 29 minutes + 47 minutes, те уложился в 4 часа
		
	

	9) проектирование апи для searchBar

			ожидалось - 2 часа, фактически -    2 часа
		
	10) отладка работы searchBar
	
			ожидалось - 2 часа, фактически -  первично да, 2 часа. проблемы опишу ниже

	11) верстка 2.0.0

			ожидалось - 2 часа, фактически - в пределах 30 минут.
			потерял нить и этот экран упал в какой то коммит, но сложностей с ним вообще не было

	11) верстка критическая ошибка

		 	ожидалось - 2 часа, но контроль фактического времени начал ложиться.
			на этом этапе начал пробовать сводить в тестовом режиме стейты экранов воедино, а также
		  	захотел сделать хороший state hoisting для компоуз, и логично заменеджить стэйты в VM
	
			тайм трекер показывает Took 4 hours 29 minutes для этого коммита
	 
далее я перестал четко придерживаться заранее намеченного плана, 
поскольку на этом этапе выявил темы, которые не смог декомпозировать на старте и заложить в тайм фрейм
Сначала опишу таски из первоначального плана, а затем то, что проявилось
 
	 13) верстка ошибка поиска

			  Ожидалось - 2 часа, фактически - в пределах 30 минут.
        

	14) верстка экрана профиля

			 Ожидалось - 2 часа, фактически - в пределах 30 минут.

	13) ручное тестирование, поиск неисправностей

			N

	14) устранение неисправностей, рефакторинг

   			N

	итого 36 + N  
 
 действительно, реальное "что-то" я имел на 37й час почти чистого времени разработки по тайм трекеру.
 по факту - я затратил почти 58 часов на complete.
 ниже опишу то, о чем забыл / не знал / не думал что вызовет сложности на начальном этапе проектирования

 изначально выбрал не совсем правильные места для compose state hoisting. решил, что частичный сброс стейтов при activity onDestroy 
 не оч. и захотел заменеджить.
 понял, что лучше бы заюзать навигацию. добавил. 
 
 в итоге 

 	add EmployeeAppState; decide attach compose navigation
	Took 41 minutes
 
 	fix project structure
	Took 2 hours 48 minutes
	Took 3 hours 15 minutes (--amend)

 далее я снова перечитал ТЗ, пошел править ui-шку

 	fix tab naming
	Took 29 minutes

 далее пошел тильт :/
 
	 	stub with state
		Took 2 hours 55 minutes

  		fix state behavior
		Took 5 hours 47 minutes

  		fix screen states
		Took 1 hour 47 minutes

  вся проблема в итоге по этому безобразию в построении понятной и обслуживаемой структуры кода в контексте менеджинга
  связи состояний боттом шита, трэилинг иконки сёрч бара, 
  пересмотра "где лучше в итоге и как держать стейты - в плейн @Stable классах или же в VM

  далее все пошло ок

		fix comaparable agehub, add desugaring build gradle  (в первоначальном плане я позабыл о том, что нужно
  			спроектировать механизм построения списка оносительно определенных критериев модели - "дня рождения")
		Took 1 hour 10 minutes 

  		complete birthday screen ui logic
		Took 1 hour 8 minutes

		add dial intent
		Took 1 hour 5 minutes

  		add and set icons 
		Took 4 minutes	

		beta release
		Took 15 minutes

Итого. планировал 36 + N. complete - 57. 


	

