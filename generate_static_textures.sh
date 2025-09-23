#!/bin/bash

# Generate static texture files for all chemicals by combining layer textures
TEXTURES_DIR="src/main/resources/assets/chemlibmekanized/textures/items"
OUTPUT_DIR="src/main/resources/assets/chemlibmekanized/textures/items/chemicals"
ELEMENTS_FILE="/home/keroppi/Development/Minecraft/ChemLib/src/main/resources/data/chemlib/elements.json"
COMPOUNDS_FILE="/home/keroppi/Development/Minecraft/ChemLib/src/main/resources/data/chemlib/compounds.json"

echo "Generating static texture files for all chemicals..."

# Create output directory
mkdir -p "$OUTPUT_DIR"

# Function to combine textures using ImageMagick
combine_textures() {
    local item_name="$1"
    local template_name="$2"
    local layer0="$TEXTURES_DIR/${template_name}_layer_0.png"
    local layer1="$TEXTURES_DIR/${template_name}_layer_1.png"
    local output="$OUTPUT_DIR/${item_name}.png"

    if [[ -f "$layer0" ]]; then
        if [[ -f "$layer1" ]]; then
            # Combine both layers
            magick composite "$layer1" "$layer0" "$output"
        else
            # Just copy layer 0
            cp "$layer0" "$output"
        fi
        echo "Generated texture: $item_name -> $template_name"
    else
        echo "Warning: Base texture not found: $layer0"
    fi
}

# Function to get element template
get_element_template() {
    local matter_state="$1"
    case "$matter_state" in
        "solid") echo "element_solid" ;;
        "liquid") echo "element_liquid" ;;
        "gas") echo "element_gas" ;;
        *) echo "element_solid" ;;  # fallback
    esac
}

# Function to get compound template
get_compound_template() {
    local name="$1"
    local matter_state="$2"

    # Special logic for dust compounds
    if [[ "$matter_state" == "solid" ]]; then
        local name_lower=$(echo "$name" | tr '[:upper:]' '[:lower:]')
        if [[ "$name_lower" == *"oxide"* || "$name_lower" == *"sulfate"* ||
              "$name_lower" == *"chromate"* || "$name_lower" == *"purple"* ||
              "$name_lower" == *"iodide"* ]]; then
            echo "compound_dust"
            return
        fi
    fi

    case "$matter_state" in
        "solid") echo "compound_solid" ;;
        "liquid") echo "compound_liquid" ;;
        "gas") echo "compound_gas" ;;
        *) echo "compound_solid" ;;  # fallback
    esac
}

# Check if ImageMagick is available
if ! command -v magick &> /dev/null; then
    echo "ImageMagick is required but not installed. Installing..."
    # Try to install ImageMagick
    if command -v apt-get &> /dev/null; then
        sudo apt-get update && sudo apt-get install -y imagemagick
    elif command -v pacman &> /dev/null; then
        sudo pacman -S imagemagick
    else
        echo "Please install ImageMagick manually and re-run this script."
        exit 1
    fi
fi

# Process elements
echo "Processing elements..."
while IFS= read -r line; do
    if echo "$line" | grep -q '"name":'; then
        name=$(echo "$line" | sed 's/.*"name": *"\([^"]*\)".*/\1/')
        # Read the next few lines to get matter_state
        matter_state=""
        for i in {1..10}; do
            read -r next_line
            if echo "$next_line" | grep -q '"matter_state":'; then
                matter_state=$(echo "$next_line" | sed 's/.*"matter_state": *"\([^"]*\)".*/\1/')
                break
            fi
        done

        template_name=$(get_element_template "$matter_state")
        combine_textures "$name" "$template_name"
    fi
done < <(grep -A 10 '"name":' "$ELEMENTS_FILE")

# Process compounds
echo "Processing compounds..."
while IFS= read -r line; do
    if echo "$line" | grep -q '"name":'; then
        name=$(echo "$line" | sed 's/.*"name": *"\([^"]*\)".*/\1/')
        # Read the next few lines to get matter_state
        matter_state=""
        for i in {1..15}; do
            read -r next_line
            if echo "$next_line" | grep -q '"matter_state":'; then
                matter_state=$(echo "$next_line" | sed 's/.*"matter_state": *"\([^"]*\)".*/\1/')
                break
            fi
        done

        template_name=$(get_compound_template "$name" "$matter_state")
        combine_textures "$name" "$template_name"
    fi
done < <(grep -A 15 '"name":' "$COMPOUNDS_FILE")

echo "Static texture generation completed!"
echo "Generated textures are in: $OUTPUT_DIR"