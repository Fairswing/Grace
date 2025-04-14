# GRACE

**GRACE** stands for **Generic Risk Assessment and Cancer Evaluation** — a Java-based project developed for **learning purposes**.  
Its primary goal is to explore how Artificial Intelligence works under the hood, by building a simple neural network library from scratch, complete with **backpropagation** and **gradient descent**.

---

## 📚 Purpose

This project was built to:
- Understand how basic neural networks function internally.
- Implement forward and backward passes manually (no external ML libraries).
- Learn the principles of gradient descent and weight updates.
- Create a basic AI-driven evaluation system for medical-like data.

---

## 🔧 Key Features

### 🤖 Neural Network Implementation
- **Custom NN Library**: Pure Java implementation of a feedforward neural network.
- **Backpropagation**: Gradient calculation and weight update via backpropagation.
- **Training with Gradient Descent**: Batch training with loss minimization.
- **Flexible Architecture**: Define input size, hidden layers, activation functions, and outputs.

### 📊 Data Evaluation
- **CSV Parsing**: Load structured data from `.csv` files for training and evaluation.
- **Risk Simulation**: Use neural network predictions to assess risk factors.

### 🎨 Graphical Interface
- **Visualization**: Visualize how the neural network weights and biases change during training.

---

## 🧰 Installation & Setup

### 🔨 Requirements
- Java JDK 17+
- [JavaFX SDK](https://openjfx.io/)
- [JavaCSV Library](https://www.csvreader.com/java_csv.php)
- IDE with JavaFX support (IntelliJ, Eclipse, VS Code)

### ⚙️ Eclipse Configuration

#### Import Project:
**Option 1: Clone from Git**
```bash
git clone https://github.com/andreicscs/FabricSimulation.git
```
Then in Eclipse:

File → Import → Existing Projects into Workspace → Select root directory → Browse to the cloned folder

##
**Option 2: Download Project Folder**

File → Import → Existing Projects into Workspace → Select root directory → Browse to the downloaded folder

### Add JavaFX and JavaCSV Libraries:
Right-click project → Build Path → Configure Build Path → Add the .jar files from both JavaFX and JavaCSV

#### VM Arguments (Run Configurations):
--module-path "path/to/javafx-sdk-19/lib" --add-modules javafx.controls,javafx.fxml
> Replace `"path/to/javafx-sdk-19/lib"` with your actual JavaFX SDK path.
