#!/bin/bash

# Generate static model files for all chemicals using standard item/generated models
MODELS_DIR="src/main/resources/assets/chemlibmekanized/models/item"
ELEMENTS_FILE="/home/keroppi/Development/Minecraft/ChemLib/src/main/resources/data/chemlib/elements.json"
COMPOUNDS_FILE="/home/keroppi/Development/Minecraft/ChemLib/src/main/resources/data/chemlib/compounds.json"

echo "Generating static model files for all chemicals..."

# Create models directory
mkdir -p "$MODELS_DIR"

# Function to create a static model file
create_static_model() {
    local item_name="$1"
    local model_file="$MODELS_DIR/${item_name}.json"

    cat > "$model_file" << EOF
{
  "parent": "item/generated",
  "textures": {
    "layer0": "chemlibmekanized:item/chemicals/${item_name}"
  }
}
EOF
    echo "Generated static model: $item_name"
}

# Process elements
echo "Processing elements..."
while IFS= read -r line; do
    if echo "$line" | grep -q '"name":'; then
        name=$(echo "$line" | sed 's/.*"name": *"\([^"]*\)".*/\1/')
        create_static_model "$name"
    fi
done < <(grep '"name":' "$ELEMENTS_FILE")

# Process compounds
echo "Processing compounds..."
while IFS= read -r line; do
    if echo "$line" | grep -q '"name":'; then
        name=$(echo "$line" | sed 's/.*"name": *"\([^"]*\)".*/\1/')
        create_static_model "$name"
    fi
done < <(grep '"name":' "$COMPOUNDS_FILE")

echo "Static model generation completed!"