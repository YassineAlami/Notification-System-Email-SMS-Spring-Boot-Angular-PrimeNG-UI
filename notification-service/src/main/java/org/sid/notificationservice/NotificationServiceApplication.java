package org.sid.notificationservice;

import org.sid.notificationservice.dto.UserDTO;
import org.sid.notificationservice.model.*;
import org.sid.notificationservice.repository.*;
import org.sid.notificationservice.service.DeliveryService;
import org.sid.notificationservice.service.SectorService;
import org.sid.notificationservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateDatabaseSectors(SectorRepository sectorRepository) {
		return args -> {
			// Populate the database with sample sectors
			Sector sector = Sector.builder()
					.name("CDG Prévoyance").description("CDG Prévoyance, la Branche Prévoyance de la Caisse de Dépôt et de Gestion, incarne sa vocation de tiers de confiance et assure la gestion de deux organismes publics, CNRA et RCAR.").build();
			sectorRepository.save(sector);
			sector = Sector.builder()
					.name("CDG Invest").description("CDG Invest est un investisseur et gestionnaire de fonds de capital investissement qui intervient autour des grands enjeux de l’économie marocaine : diversification productive, régionalisation, développement des PPP, développement de l’entrepreneuriat, et développement durable.").build();
			sectorRepository.save(sector);
			sector = Sector.builder()
					.name("OCP").description("Créé en 1920 pour extraire et exploiter le phosphate marocain, les opérations de notre Groupe se sont élargies pour couvrir aujourd’hui l’ensemble de la chaîne de valeur des engrais.").build();
			sectorRepository.save(sector);
		};
	}

	@Bean
	public CommandLineRunner populateDatabaseActors(UserRepository userRepository, SectorRepository sectorRepository) {
		return args -> {
			List<Sector> sectors = sectorRepository.findAll();

			// Populate the database with sample data
			/*User user = User.builder()
					.username("Nabil").password("123").email("tajabrite@cdgprevoyance.ma").phoneNumber("0661856448").sector(sectors.get(new Random().nextInt(3))).build();
			userRepository.save(user);*/
			User user = User.builder()
					.username("Nabil").password("123").email("taj@cdgprevoyance.ma").phoneNumber("0661858585").sector(sectors.get(0)).build();
			userRepository.save(user);
			user = User.builder()
					.username("Yassine").password("123").email("m.yassine.alami@gmail.com").phoneNumber("0669696969").sector(sectors.get(0)).build();
			userRepository.save(user);
			user = User.builder()
					.username("Ahmed").password("123").email("ahmed.ali.alami111@gmail.com").phoneNumber("0612121212").sector(sectors.get(1)).build();
			userRepository.save(user);
			user = User.builder()
					.username("Lina").password("123").email("lina@gmail.com").phoneNumber("0711111111").sector(sectors.get(2)).build();
			userRepository.save(user);
		};
	}


	@Bean
	public CommandLineRunner populateDatabaseNotificationTypes(NotificationTypeRepository notificationTypeRepository) {
		return args -> {
			// Check if notification types already exist in the database
			if (notificationTypeRepository.count() == 0) {
				// Create sample notification types using builder pattern
				List<NotificationType> sampleTypes = Arrays.asList(
						NotificationType.builder().name("Event").build(),
						NotificationType.builder().name("Salary Change").build(),
						NotificationType.builder().name("New Policy").build(),
						//V2
						NotificationType.builder().name("Meeting").build(),
						NotificationType.builder().name("Changement de Pension").build()
				);

				// Save sample notification types to the database
				notificationTypeRepository.saveAll(sampleTypes);
			}
		};
	}


	@Bean
	public CommandLineRunner populateDatabaseDeliveryMethods(DeliveryMethodRepository deliveryMethodRepository) {
		return args -> {
			// Check if delivery methods already exist in the database
			if (deliveryMethodRepository.count() == 0) {
				// Create sample delivery methods using builder pattern
				List<DeliveryMethod> sampleDeliveryMethods = Arrays.asList(
						DeliveryMethod.builder()
								.name("Email")
								.description("Description of Email Delivery Method")
								.build(),
						DeliveryMethod.builder()
								.name("SMS")
								.description("Description of SMS Delivery Method")
								.build(),
						DeliveryMethod.builder()
								.name("Call")
								.description("Description of Call Delivery Method")
								.build(),
						//V2
						DeliveryMethod.builder()
								.name("Mail")
								.description("Description of Mail Delivery Method")
								.build()
						// Add more sample delivery methods as needed
				);

				// Save sample delivery methods to the database
				deliveryMethodRepository.saveAll(sampleDeliveryMethods);
			}
		};
	}

	@Bean
	public CommandLineRunner populateDatabaseDelivery(DeliveryRepository deliveryRepository,
													  DeliveryMethodRepository deliveryMethodRepository)throws Exception {
		return args -> {
			if (deliveryRepository.count() == 0) {
				// Retrieve delivery methods from the database
				List<DeliveryMethod> deliveryMethods = deliveryMethodRepository.findAll();

				// Create sample deliveries using builder pattern
				List<Delivery> sampleDeliveries = Arrays.asList(
						Delivery.builder()
								.name("Delivery 1")
								.state("sent")

								.creationDate(new Date(2024 - 1900, 4, 9, 10, 30))
								.estimatedDeliveryDate(new Date(2024 - 1900, 4, 10, 10, 30))
								.deliveryDate(new Date(2024 - 1900, 4, 10, 10, 30))

								.deliveryMethod(deliveryMethods.get(0)) // Choose the first delivery method
								.build(),
						Delivery.builder()
								.name("Delivery 2")
								/*.state("sent")
								.estimatedDeliveryDate(new Date())
								.deliveryDate(new Date())*/
								.creationDate(new Date(2024 - 1900, 4, 15, 10, 30))
								.estimatedDeliveryDate(new Date(2024 - 1900, 4, 17, 10, 30))
								.deliveryMethod(deliveryMethods.get(1)) // Choose the second delivery method
								.build(),
						Delivery.builder()
								.name("Delivery 3")
								.state("pending")
								.creationDate(new Date(2024 - 1900, 4, 20, 10, 30))
								.estimatedDeliveryDate(new Date(2024 - 1900, 4, 21, 10, 30))
								/*.deliveryDate(new Date())*/
								.deliveryMethod(deliveryMethods.get(2)) // Choose the second delivery method
								.build(),
						Delivery.builder()
								.name("Delivery 4")
								.state("pending")
								.creationDate(new Date(2024 - 1900, 4, 25, 10, 30))
								.estimatedDeliveryDate(new Date(2024 - 1900, 4, 27, 10, 30))
								.deliveryMethod(deliveryMethods.get(0)) // Choose the second delivery method
								.build(),
						Delivery.builder()
								.name("Delivery 5")
								.state("pending")
								.creationDate(new Date(2024 - 1900, 4, 25, 10, 30))
								.estimatedDeliveryDate(new Date(2024 - 1900, 4, 27, 10, 30))
								.deliveryMethod(deliveryMethods.get(1)) // Choose the second delivery method
								.build()
						// Add more sample deliveries as needed
				);

				// Save sample deliveries to the database
				deliveryRepository.saveAll(sampleDeliveries);
			}
		};
	}



	@Bean
	public CommandLineRunner populateDatabaseNotifications(NotificationRepository notificationRepository,
			SectorService sectorService, UserService userService, DeliveryRepository deliveryRepository,
			NotificationTypeRepository notificationTypeRepository) {
		return args -> {
			//List<UserDTO> users = userService.getAllUsers();
			//List<Sector> sectors = sectorService.getAllSectors();
			List<NotificationType> notificationTypes = notificationTypeRepository.findAll();
			List<Delivery> deliveries = deliveryRepository.findAll();

			// Create 20 sample notifications and associate them with users
			List<Notification> notifications = new ArrayList<>();

			//V1
			/*for (int i = 1; i <= 3; i++) {
				// Choose a random notification type from existing types
				NotificationType type = notificationTypes.get(new Random().nextInt(notificationTypes.size()));

				Notification notification = new Notification();
				notification.setType(type);
				notification.setContent("Content " + i);
				notification.setCreationDate(new Date());
				notification.setDelivery(deliveries.get(0));
				notifications.add(notification);
			}
			// Save the notifications to the database
			notificationRepository.saveAll(notifications);*/

			//V2
			Notification notification = new Notification();
			notification.setType(notificationTypes.get(1));
			notification.setContent("Content 1 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			notification.setDelivery(deliveries.get(0));
			notifications.add(notification);
			notificationRepository.saveAll(notifications);

			notification = new Notification();
			notification.setType(notificationTypes.get(1));
			notification.setContent("Content 2 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			notification.setDelivery(deliveries.get(0));
			notifications.add(notification);
			notificationRepository.saveAll(notifications);

			notification = new Notification();
			notification.setType(notificationTypes.get(3));
			notification.setContent("Content 3 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			notification.setDelivery(deliveries.get(1));
			notifications.add(notification);
			notificationRepository.saveAll(notifications);

			notification = new Notification();
			notification.setType(notificationTypes.get(1));
			notification.setContent("Content 4 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			notification.setDelivery(deliveries.get(2));
			notifications.add(notification);
			notificationRepository.saveAll(notifications);

			notification = new Notification();
			notification.setType(notificationTypes.get(2));
			notification.setContent("Content 5 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			notification.setDelivery(deliveries.get(2));
			notifications.add(notification);
			notificationRepository.saveAll(notifications);


			notification = new Notification();
			notification.setType(notificationTypes.get(2));
			notification.setContent("Content 6 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			/*notification.setDelivery(deliveries.get(2));*/
			notifications.add(notification);
			notificationRepository.saveAll(notifications);

			notification = new Notification();
			notification.setType(notificationTypes.get(2));
			notification.setContent("Content 7 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			/*notification.setDelivery(deliveries.get(2));*/
			notifications.add(notification);
			notificationRepository.saveAll(notifications);

			notification = new Notification();
			notification.setType(notificationTypes.get(2));
			notification.setContent("Content 8 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			/*notification.setDelivery(deliveries.get(2));*/
			notifications.add(notification);
			notificationRepository.saveAll(notifications);


			notification = new Notification();
			notification.setType(notificationTypes.get(3));
			notification.setContent("Content 9 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			notification.setDelivery(deliveries.get(1));
			notifications.add(notification);
			notificationRepository.saveAll(notifications);



			notification = new Notification();
			notification.setType(notificationTypes.get(1));
			notification.setContent("Content 10 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			notification.setDelivery(deliveries.get(2));
			notifications.add(notification);
			notificationRepository.saveAll(notifications);



			notification = new Notification();

			notification.setType(notificationTypes.get(3));
			notification.setContent("Content 11 test" );
			notification.setCreationDate(new Date(2024 - 1900, 4, 17, 10, 30));
			notification.setDelivery(deliveries.get(1));
			notifications.add(notification);
			notificationRepository.saveAll(notifications);

		};
	}


	@Bean
	public CommandLineRunner populateDatabaseTemplates(TemplateRepository templateRepository,
			NotificationTypeRepository notificationTypeRepository) {
		return args -> {
			// Check if templates already exist in the database
			if (templateRepository.count() == 0) {

				// Create sample templates
				List<Template> sampleTemplates = Arrays.asList(
						Template.builder().title("Upcoming Event Notification")
								.body("Dear [$Recipient Name],\n\nWe are pleased to inform you about an upcoming event that will take place on [$date].\nThe event will be held at [$location] and will start at [$time].\nPlease mark your calendar and make sure to attend this exciting event.\n\nBest regards,\nCDG Prévoyance Team")
								.creationDate(new Date())
								.notificationType(notificationTypeRepository.findById(1L).get())
								.build(),
						Template.builder().title("Event Cancellation Notice")
								.body("Dear [$Recipient Name],\n\nWe regret to inform you that the [$name] scheduled for [$date] at [$location] has been cancelled.\nDue to unforeseen circumstances : [$reason], \nwe have had to make the difficult decision to cancel the event.\nWe apologize for any inconvenience this may cause and appreciate your understanding.\n\n" +
										"If you have any questions or concerns, please feel free to contact us at [$contact].\n\nBest regards,\nCDG Prévoyance Team")
								.creationDate(new Date())
								.notificationType(notificationTypeRepository.findById(1L).get())
								.build(),
						Template.builder().title("Salary Adjustment Notification")
								.body("Dear [$Recipient Name],\n\nWe would like to inform you about an upcoming change to your salary.\nStarting from [$date], your salary will be adjusted to [$salary] Dh.\nThis change is in accordance with : [$reason], and we believe it reflects your contributions and performance.\n\nIf you have any questions or concerns regarding this change, please feel free to contact the HR department.\n\nBest regards,\nCDG Prévoyance Team")
								.creationDate(new Date())
								.notificationType(notificationTypeRepository.findById(2L).get())
								.build(),
						Template.builder().title("Important Update: New Company Policy")
								.body("Dear Team,\n\nWe are pleased to announce a new policy that will be implemented at [$company] effective [$date].\nThis policy aims to [$description of new policy].\nIt is important for all employees to familiarize themselves with the details outlined in the policy document, which will be distributed to you shortly.\n\nWe believe that this policy will : [$impact of new policy] and contribute to our collective success as a company.\n\nIf you have any questions or need clarification regarding this new policy, please do not hesitate to reach out to your supervisor or the HR department.\n\nThank you for your attention to this matter.\n\nBest regards,\nCDG Prévoyance Team")
								.creationDate(new Date())
								.notificationType(notificationTypeRepository.findById(3L).get())
								.build(),
						//V2
						Template.builder().title("Upcoming Meeting Notification")
								.body("Dear [$Recipient Name],\n\nThis is a friendly reminder about our upcoming meeting, [Meeting Topic], scheduled for [Date] at [Time].\n\nWe will be discussing [meeting's overview]. Your presence and input are highly valued.,\nCDG Prévoyance Team")
								.creationDate(new Date())
								.notificationType(notificationTypeRepository.findById(4L).get())
								.build(),
						Template.builder().title("Modification des Règles Régissant les Pensions de Retraite")
								.body("Dear [$Recipient Name],\n\nNous vous informons que des modifications importantes ont été apportées aux règles régissant vos pensions de retraite.\nCes modifications entreront en vigueur le [$date] et affecteront les droits à pension de tous les participants.\nAvant ces modifications, le régime de retraite fonctionnait comme suit:\n[$régimes avant]\nLe régime de retraite fonctionnera désormais comme suit:\n[$régimes apres]\n\nNous comprenons que ces changements peuvent susciter des questions et des inquiétudes.\nsi vous avez des questions n'hésitez pas à contacter [$contact]\nCDG Prévoyance Team")
								.creationDate(new Date())
								.notificationType(notificationTypeRepository.findById(5L).get())
								.build(),
						Template.builder().title("Passage d'un Type de Pension à un Autre")
								.body("Dear [$Recipient Name],\n\nThis is a friendly reminder about our upcoming meeting, [Meeting Topic], scheduled for [Date] at [Time].\n\nWe will be discussing [meeting's overview]. Your presence and input are highly valued.,\nCDG Prévoyance Team")
								.creationDate(new Date())
								.notificationType(notificationTypeRepository.findById(5L).get())
								.build()
						// Add more sample templates as needed
				);

				// Save sample templates to the database
				templateRepository.saveAll(sampleTemplates);
			}
		};
	}



}