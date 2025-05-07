#!/bin/bash

# 1. Build du projet avec Gradle
echo "🔨 Construction de l'application avec Gradle..."
./gradlew build || { echo "❌ Échec du build Gradle"; exit 1; }

# 2. Vérification/Création du dossier target
echo "📂 Vérification du dossier targetBuild..."
if [ ! -d "targetBuild" ]; then
  echo "Création du dossier targetBuild..."
  mkdir targetBuild || { echo "❌ Impossible de créer le dossier targetBuild"; exit 1; }
fi

# 3. Copie du fichier JAR
echo "📦 Copie du fichier JAR..."
cp build/libs/validation-tolls-0.0.1-SNAPSHOT.jar targetBuild/ || { echo "❌ Échec de la copie du JAR"; exit 1; }

echo "✅ Déploiement terminé avec succès !"