import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';

class PatientProfile extends StatefulWidget {
  const PatientProfile({super.key});

  @override
  State<PatientProfile> createState() => _PatientProfileState();
}

class _PatientProfileState extends State<PatientProfile> {
  // Controllers for profile details
  final TextEditingController nameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController phoneController = TextEditingController();

  // State for profile picture
  File? _profilePicture;

  // Function to pick an image from the gallery
  Future<void> _pickProfilePicture() async {
    final ImagePicker picker = ImagePicker();
    final XFile? pickedFile = await picker.pickImage(source: ImageSource.gallery);

    if (pickedFile != null) {
      setState(() {
        _profilePicture = File(pickedFile.path);
      });
    }
  }

  // Function to fetch patient data (placeholder)
  Future<void> _fetchPatientData() async {
    // Simulate an API call to fetch patient data
    setState(() {
      nameController.text = "John Doe";
      emailController.text = "john.doe@example.com";
      phoneController.text = "123-456-7890";
      // Set an existing profile picture if available
      // _profilePicture = File('path_to_existing_profile_picture');
    });
  }

  // Function to save patient data (placeholder)
  Future<void> _savePatientData() async {
    // Simulate saving data to an API
    final updatedData = {
      "name": nameController.text,
      "email": emailController.text,
      "phone": phoneController.text,
      "profilePicture": _profilePicture?.path ?? "No Picture",
    };

    // Mock success
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Profile updated successfully!')),
    );

    print("Updated Data: $updatedData"); // Replace with API call
  }

  @override
  void initState() {
    super.initState();
    _fetchPatientData(); // Fetch patient data on screen load
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Patient Profile'),
        actions: [
          IconButton(
            icon: const Icon(Icons.save),
            onPressed: _savePatientData,
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: ListView(
          children: [
            // Profile Picture
            Center(
              child: Stack(
                children: [
                  CircleAvatar(
                    radius: 60,
                    backgroundImage:
                    _profilePicture != null ? FileImage(_profilePicture!) : null,
                    child: _profilePicture == null
                        ? const Icon(Icons.person, size: 60)
                        : null,
                  ),
                  Positioned(
                    bottom: 0,
                    right: 0,
                    child: InkWell(
                      onTap: _pickProfilePicture,
                      child: const CircleAvatar(
                        radius: 20,
                        backgroundColor: Colors.blue,
                        child: Icon(Icons.edit, color: Colors.white),
                      ),
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20),
            // Name Field
            TextFormField(
              controller: nameController,
              decoration: const InputDecoration(labelText: 'Name'),
            ),
            const SizedBox(height: 20),
            // Email Field
            TextFormField(
              controller: emailController,
              decoration: const InputDecoration(labelText: 'Email'),
              keyboardType: TextInputType.emailAddress,
            ),
            const SizedBox(height: 20),
            // Phone Field
            TextFormField(
              controller: phoneController,
              decoration: const InputDecoration(labelText: 'Phone'),
              keyboardType: TextInputType.phone,
            ),
            const SizedBox(height: 20),
            // Save Button
            ElevatedButton(
              onPressed: _savePatientData,
              child: const Text('Save Changes'),
            ),
          ],
        ),
      ),
    );
  }
}
