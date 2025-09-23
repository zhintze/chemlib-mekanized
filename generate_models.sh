#!/bin/bash

# Generate model files for chemical items based on their matter states
MODELS_DIR="src/main/resources/assets/chemlibmekanized/models/item"
ELEMENTS_FILE="/home/keroppi/Development/Minecraft/ChemLib/src/main/resources/data/chemlib/elements.json"
COMPOUNDS_FILE="/home/keroppi/Development/Minecraft/ChemLib/src/main/resources/data/chemlib/compounds.json"

echo "Generating chemical item models..."

# Function to create a model file
create_model() {
    local item_name="$1"
    local template_name="$2"
    local model_file="$MODELS_DIR/${item_name}.json"

    cat > "$model_file" << EOF
{
  "parent": "chemlibmekanized:item/${template_name}"
}
EOF
    echo "Generated: $item_name -> $template_name"
}

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

        case "$matter_state" in
            "solid")
                create_model "$name" "element_solid_model"
                ;;
            "liquid")
                create_model "$name" "element_liquid_model"
                ;;
            "gas")
                create_model "$name" "element_gas_model"
                ;;
            *)
                create_model "$name" "element_solid_model"  # fallback
                ;;
        esac
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

        template_name=""
        case "$matter_state" in
            "solid")
                # Check for dust compounds
                if echo "$name" | grep -E "(oxide|sulfate|chromate|purple|iodide)" > /dev/null; then
                    template_name="compound_dust_model"
                else
                    template_name="compound_solid_model"
                fi
                ;;
            "liquid")
                template_name="compound_liquid_model"
                ;;
            "gas")
                template_name="compound_gas_model"
                ;;
            *)
                template_name="compound_solid_model"  # fallback
                ;;
        esac

        create_model "$name" "$template_name"
    fi
done < <(grep -A 15 '"name":' "$COMPOUNDS_FILE")

echo "Model generation completed!"