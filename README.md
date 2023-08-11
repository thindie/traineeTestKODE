# traineeTestKODE

### kode android trainee test

#### stack: kotlin, compose, retrofit, coroutines, mvvm, hilt, coil
2. decomposition
2 часа	
1. подготовка среды - настройка gradle, 
		git init
		старт data-слоя: 
		repository, retrofit.
		определение моделей, мапперов

    * ожидалось - 2 часа,
    * фактически - Took 39 minutes
3. прокидывание di, ручной тест прилета данных из сети
    * ожидалось - 2 часа,
    * фактически - Took 1 hour 25 minutes
3. верстка searchBar, верстка tabSelector
    * ожидалось - 2 часа
    * фактически - 1 hour 15 minutes
4. верстка скелетона
    * ожидалось - 2 часа,
    *  фактически - 10 minutes (заюзал из прошлых pet)	
5. верстка listUnit
    * ожидалось - 2 часа,
    * фактически - Took 1 hour 58 minutes
7. импорт необходимых иконок из фигма + FONTS
    * Took 10 minutes + Took 53 minutes --- шрифты забыл внести в план и не декомпозировал
8. секция custom notification bar
    * 4 часа: поиск и изучение теории
    * 2 часа + n: имплементация
    * временные затраты не ясны, ввиду незнакомой технологии

			по нотификейшен бару - оставил базовый андроид, ввиду того, что прочитал доку на developers.
			этот момент скользкий и его надо обсуждать со старшими кодерами / дизайнерами / etc
			однако я приготовил api на 80%, его можно заюзать в тестовом режиме - будет выглядеть как на дизайн-проекте в фигма
			!!затрачено около 6 часов в сумме.
		 ### UPD по предыдущему пункту at 11.08.23: LOL
			

	
10. cекция composebottom sheet
    * 4 часа: поиск и изучение теории
    * 4 часа + n: имплементация
    * временные затраты не ясны, ввиду незнакомой технологии   
    * Took 2 hours 29 minutes + 47 minutes, те уложился в 4 часа
11. проектирование апи для searchBar
    * ожидалось - 2 часа
    * фактически -    2 часа	
12. отладка работы searchBar
    * ожидалось - 2 часа
    * фактически - 2 часа (проблемы опишу ниже)
13. верстка 2.0.0
    * ожидалось - 2 часа, фактически - в пределах 30 минут.
14. верстка критическая ошибка
    * ожидалось - 2 часа, но контроль фактического времени начал ложиться :[		

			далее я перестал четко придерживаться заранее намеченного плана, 
			поскольку на этом этапе выявил темы, которые не смог декомпозировать на старте и заложить в тайм фрейм.
			Сначала опишу таски из первоначального плана, а затем то, что проявилось
15. верстка ошибка поиска
    * Ожидалось - 2 часа
    * фактически - в пределах 30 минут.     
16. верстка экрана профиля
    * Ожидалось - 2 часа
    * фактически - в пределах 30 минут.
17. ручное тестирование, поиск неисправностей
    * N
18. устранение неисправностей, рефакторинг
    * N
## итого 36 + N  
 
	 MVP я имел на 37й час почти чистого времени разработки по тайм трекеру.
	 по факту - я затратил почти 58 часов на complete.
	 ниже опишу то, о чем забыл / не знал / не думал что вызовет сложности на начальном этапе проектирования
	
	 изначально выбрал не совсем правильные места для compose state hoisting. решил, что частичный сброс стейтов при activity onDestroy 
	 это bad case и захотел заменеджить, и захэндлить landscape orientation.
	 также, понял, что лучше бы заюзать навигацию. добавил. 
  
* add EmployeeAppState; decide attach compose navigation Took 41 minutes
* fix project structure	Took 6 hours
* fix tab naming Took 29 minutes
* stub with state Took 2 hours 55 minutes
* fix state behavior Took 5 hours 47 minute
* fix screen states Took 1 hour 47 minutes

	  вся проблема в итоге по этому безобразию в построении понятной и обслуживаемой структуры кода в контексте менеджинга
	  связи состояний боттом шита, трэилинг иконки сёрч бара, 
	  пересмотра "где лучше в итоге и как держать стейты - в плейн @Stable классах или же в VM
	  далее все пошло ок

* fix comaparable agehub, add desugaring build gradle  (в первоначальном плане я позабыл о том,
что нужно cпроектировать механизм построения
списка оносительно определенных критериев модели - "дня рождения")  - Took 1 hour 10 minutes 

* complete birthday screen ui logic Took 1 hour 8 minutes

* add dial intent Took 1 hour 5 minutes

* add and set icons Took 4 minutes	

* beta release	Took 15 minutes

### Итого. планировал 36 + N. complete - 57. 
![Screenshot_7](https://github.com/thindie/traineeTestKODE/assets/93595798/c9bb67dd-9af8-4b00-bd50-12e660004141)
![Screenshot_8](https://github.com/thindie/traineeTestKODE/assets/93595798/cb373a5e-e701-4327-909b-17c2646bebb8)
![Screenshot_9](https://github.com/thindie/traineeTestKODE/assets/93595798/36e6e1bb-2efe-4b0c-b000-d365d47d3c6e)
![Screenshot_10](https://github.com/thindie/traineeTestKODE/assets/93595798/14837a4d-dce3-4591-a4cf-6c1772b8c41b)
![Screenshot_11](https://github.com/thindie/traineeTestKODE/assets/93595798/70e19fdc-e708-4b51-a64b-41a6b582bad5)
![Screenshot_12](https://github.com/thindie/traineeTestKODE/assets/93595798/c1836250-0394-446b-bf73-a632012d4efe)
![Screenshot_13](https://github.com/thindie/traineeTestKODE/assets/93595798/89ad4a8b-72c3-4967-a86a-77fb24966f4a)
![Screenshot_14](https://github.com/thindie/traineeTestKODE/assets/93595798/7a43bbc9-31d1-402a-87c5-99c240e611e1)




	

