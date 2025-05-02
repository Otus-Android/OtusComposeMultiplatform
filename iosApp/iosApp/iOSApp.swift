import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            MainView()
                .ignoresSafeArea()
        }
    }
}

class SwiftUIViewFactory: NativeViewFactory {
    static var shared = SwiftUIViewFactory()
    
    func createBarcodeScannerView(onDetected: @escaping (String) -> Void) -> UIViewController {
        let view = QrScannerView(onBarcodeDetected: onDetected)
        
        return UIHostingController(rootView: view)
    }
}

struct MainView: UIViewControllerRepresentable {
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        
    }
    
    func makeUIViewController(context: Context) -> some UIViewController {
        MainKt.MainUIViewController(nativeViewFactory: SwiftUIViewFactory.shared)
    }
}
