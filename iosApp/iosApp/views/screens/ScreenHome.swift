import SwiftUI
import Combine
import shared

struct ContentView: View {
    @ObservedObject private var viewModel: ViewModel = ViewModel()
    @State private var isDisableDownloadButton = false

    init() {
        viewModel.getGenres()
    }

    var body: some View {
        NavigationView {
            VStack {
                Text("Selecciona un género para comenzar")
                List(viewModel.genresList, id: \.self) { item in
                    NavigationLink(item.name!) {
                        ScreenListMovies(genreItem: item)
                    }
                }
            }
                    .navigationBarTitle("Géneros")
        }
    }
}

class ViewModel: ObservableObject {
    @Published var genresList: [Genre] = []

    func getGenres() {
        Repository().getGenres { result, error in
            DispatchQueue.main.async {
                self.genresList = result?.genres ?? []
            }
        }
    }
}
