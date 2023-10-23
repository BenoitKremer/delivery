import pygame
import random


pygame.init()

# RAZ des variables
erreur = 1
recommencer = False
perdu = False
gagne = False
saisie_mot = ""
mot_entre = False
affiche_saisie = ""

# Chargement des mots
mots = ["PYTHON", "JAVA", "CPLUSPLUS", "JAVASCRIPT", "RUBY"]
mot_a_deviner = random.choice(mots).upper()
mot_affiche = ["_" for _ in mot_a_deviner]
lettres_tapees = []

# Polices
font = pygame.font.Font(None, 36)

# Couleurs
BLANC = (255, 255, 255)
NOIR = (0, 0, 0)

# Config de la fenetre principale
largeur = 1200
hauteur = 600
fenetre = pygame.display.set_mode((largeur, hauteur))
pygame.display.set_caption("Jeu du pendu")

# Rectangle d'affichage
rect_noir = pygame.Rect(650, 150, 200, 50)

# Ajout du background
background = pygame.image.load("HangMan/bg_farwest.jpeg")
background = pygame.transform.scale(background, (largeur, hauteur))

# Boucle du jeu
en_cours = True
while en_cours:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            en_cours = False
        elif event.type == pygame.KEYDOWN:
            if event.key >= 97 and event.key <= 122:  # Vérifie si la touche est une lettre de 'a' à 'z'
                lettre = chr(event.key).upper()
                if lettre not in lettres_tapees:
                    lettres_tapees.append(lettre)
                    if lettre in mot_a_deviner:
                        for i in range(len(mot_a_deviner)):
                            if mot_a_deviner[i] == lettre:
                                mot_affiche[i] = lettre
                    else:
                        erreur += 1

            elif event.key == pygame.K_RETURN:  # Vérifie si la touche est "Entrée"
                if not mot_entre:
                    saisie_mot = ""
                elif event.key == pygame.K_BACKSPACE:
                    saisie_mot = saisie_mot[:-1]
                else:
                    saisie_mot += event.unicode

                    # Vérifie si la saisie correspond au mot à deviner
                    if saisie_mot == mot_a_deviner:
                        gagne = True
                    else:
                        erreur += 1

    # Dessine le mot à deviner
    mot_texte = font.render(" ".join(mot_affiche), True, BLANC)

    # Dessine la zone d'écriture du mot entier
    affiche_saisie = font.render(saisie_mot, True, BLANC)

    # Dessine les lettres déjà tapées
    lettres_texte = font.render("Lettres: " + " ".join(lettres_tapees), True, BLANC)

    # Dessine un message permettant de rentrer un mot entier
    motENTIER = font.render("Pour rentrez un mot entier appuyez sur ENTRÉE", True, BLANC)

    # Affichage successif du pendu en fonction des erreurs
    if erreur >= 1 and erreur <= 7:
        pendu = pygame.image.load(f"HangMan/Le-Pendu-{erreur}.png")
    elif erreur == 8:
        if not perdu:
            pendu = pygame.image.load(f"HangMan/Le-Pendu-{erreur}.png")
            font_erreur = pygame.font.Font(None, 52)
            erreur_texte = font_erreur.render("Perdu ! Appuyez sur 'R' pour recommencer ou 'Q' pour quitter.", True, BLANC)
            perdu = True
            recommencer = True
            
            # Si le joueur veut recommencer
            if recommencer: 
                for event in pygame.event.get():
                    if event.type == pygame.KEYDOWN:
                        if event.key == pygame.K_r:
                            recommencer = False
                            erreur = 1
                            mot_a_deviner = random.choice(mots).upper()
                            mot_affiche = ["_" for _ in mot_a_deviner]
                            lettres_tapees = []
                            pendu = pygame.image.load(f"HangMan/Le-Pendu-{erreur}.png")
                            fenetre.blit(pendu, (400, 200))
                        elif event.key == pygame.K_q:
                            en_cours = False
    
    # Vérifier si le joueur à gagner + message + recommencer ou quitter possible
    if "_" not in mot_affiche and not gagne:
        font_gagne = pygame.font.Font(None, 52)
        gagne_texte = font_gagne.render("Félicitations ! Appuyez sur 'R' pour recommencer ou 'Q' pour quitter.", True, BLANC)
        gagne = True

    # affiche les contenue des variables rangé de l'arrière-plan à l'avant-plan
    # pour éviter les erreurs d'affichage (une image derrière le fond par exemple)
    # qui devient donc caché
    fenetre.blit(background, (0, 0))  # Dessine l'arrière-plan en position (0, 0)
    fenetre.blit(mot_texte, (100, 100)) # Dessine le mot à deviner
    fenetre.blit(lettres_texte, (100, 200)) # Dessine les lettres déjà rentré
    fenetre.blit(pendu, (400, 200))  # Affiche l'image du pendu
    fenetre.blit(motENTIER, (600, 40)) # Affiche la permission d'entrée un mot entier
    fenetre.blit(affiche_saisie, (600, 80)) # Affiche la zone d'écriture du mot
    pygame.draw.rect(fenetre, NOIR, rect_noir)

    # Permet l'affichage permanant des messages d'echec ou de réussite
    # Évite le clignotement
    if perdu:
        fenetre.blit(erreur_texte, (30, 450))
    elif gagne:
        fenetre.blit(gagne_texte, (30, 450))

    # Permet de recommencer ou quitter le jeu (ainsi que l'interface)
    if perdu or gagne:
        for event in pygame.event.get():
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_r:
                    erreur = 1
                    mot_a_deviner = random.choice(mots).upper()
                    mot_affiche = ["_" for _ in mot_a_deviner]
                    lettres_tapees = []
                    perdu = False
                    gagne = False
                elif event.key == pygame.K_q:
                    en_cours = False
    

    pygame.display.update()  # Met à jour l'écran

pygame.quit()